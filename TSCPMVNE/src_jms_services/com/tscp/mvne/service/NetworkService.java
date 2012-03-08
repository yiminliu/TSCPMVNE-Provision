package com.tscp.mvne.service;

import java.util.Date;

import org.apache.log4j.Logger;

import com.tscp.mvne.billing.ServiceInstance;
import com.tscp.mvne.billing.system.BillingSystem;
import com.tscp.mvne.customer.dao.DeviceInfo;
import com.tscp.mvne.customer.dao.DeviceStatus;
import com.tscp.mvne.device.DeviceException;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequest;
import com.tscp.mvne.jms.request.account.ServiceInstanceRequestType;
import com.tscp.mvne.jms.request.network.NetworkRequest;
import com.tscp.mvne.jms.request.network.NetworkRequestType;
import com.tscp.mvne.logger.TscpmvneLogger;
import com.tscp.mvne.network.NetworkException;
import com.tscp.mvne.network.NetworkInfo;
import com.tscp.mvne.network.NetworkSystem;
import com.tscp.mvne.network.exception.ESNMismatchException;
import com.tscp.mvne.network.exception.MDNMismatchException;
import com.tscp.mvne.network.exception.NetworkStatusException;
import com.tscp.mvne.service.exception.NetworkRequestException;
import com.tscp.mvne.service.exception.ServiceInstanceRequestException;
import com.tscp.mvne.service.util.NetworkServiceUtil;
import com.tscp.mvne.service.util.NetworkValidationUtil;
import com.tscp.mvne.system.provider.BillingSystemProvider;
import com.tscp.mvne.system.provider.NetworkSystemProvider;

public class NetworkService {
  protected static final Logger logger = TscpmvneLogger.getInstance();
  protected static final NetworkSystem networkSystem = NetworkSystemProvider.getInstance();
  protected static final BillingSystem billingSystem = BillingSystemProvider.getInstance();
  protected static final DeviceService deviceService = new DeviceService();
  protected static final ServiceInstanceService serviceInstanceService = new ServiceInstanceService();

  /**
   * Determining method for what actions to call.
   * 
   * @param request
   * @return
   * @throws NetworkRequestException
   * @throws NetworkStatusException
   */
  public NetworkRequest doNetworkRequest(NetworkRequest request) throws NetworkRequestException, NetworkStatusException {
    switch (request.getRequestType()) {
    case ACTIVATION:
      return activateService(request);
    case DEACTIVATION:
      return deactivateService(request);
    case REACTIVATION:
      return reserveAndActivate(request);
    case DISCONNECT:
      return disconnectService(request);
    case SUSPEND:
      return suspendService(request);
    case RESTORE:
      return restoreService(request);
    case RESERVE_AND_ACTIVATE:
      return reserveAndActivate(request);
    default:
      break;
    }
    return null;
  }

  /**
   * Activates the given NetworkInfo on the network.
   * 
   * @param networkInfo
   * @throws NetworkActivationException
   */
  protected void activate(NetworkInfo networkInfo) throws NetworkRequestException {
    try {
      networkSystem.activateMDN(networkInfo);
    } catch (NetworkException e) {
      throw new NetworkRequestException(NetworkRequestType.ACTIVATION, e.getMessage(), e.getCause());
    }
  }

  /**
   * Disconnects the given NetworkInfo on the network.
   * 
   * @param networkInfo
   * @throws NetworkDisconnectException
   */
  protected void disconnect(NetworkInfo networkInfo) throws NetworkRequestException {
    try {
      networkSystem.disconnectService(networkInfo);
    } catch (NetworkException e) {
      throw new NetworkRequestException(NetworkRequestType.DISCONNECT, e.getMessage(), e.getCause());
    }
  }

  /**
   * Restores the given NetworkInfo on the network.
   * 
   * @param networkInfo
   * @throws NetworkStatusException
   * @throws NetworkRestoreException
   */
  protected void restore(NetworkInfo networkInfo) throws NetworkRequestException, NetworkStatusException {
    try {
      if (NetworkValidationUtil.checkRestore(networkInfo)) {
        networkSystem.restoreService(networkInfo);
      }
    } catch (NetworkException e) {
      throw new NetworkRequestException(NetworkRequestType.RESTORE, e.getMessage(), e.getCause());
    }
  }

  /**
   * Suspends the given NetworkInfo on the network.
   * 
   * @param networkInfo
   * @throws NetworkRequestException
   * @throws NetworkStatusException
   */
  protected void suspend(NetworkInfo networkInfo) throws NetworkRequestException, NetworkStatusException {
    try {
      if (NetworkValidationUtil.checkSuspend(networkInfo)) {
        networkSystem.suspendService(networkInfo);
      }
    } catch (NetworkException e) {
      throw new NetworkRequestException(NetworkRequestType.SUSPEND, e.getMessage(), e.getCause());
    }
  }

  /**
   * Activates the NetworkInfo of the request and updates the associated
   * DeviceInfo.
   * 
   * @param request
   * @return
   * @throws NetworkActivationException
   * @throws NetworkDisconnectException
   */
  protected NetworkRequest activateService(NetworkRequest request) throws NetworkRequestException {
    try {
      DeviceInfo deviceInfo = request.getDeviceInfo();
      NetworkInfo networkInfo = deviceInfo.getNetworkInfo();
      activate(networkInfo);
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalId(networkInfo.getMdn());
      serviceInstance.setExternalIdType(3); // TODO move values to a enum class
      deviceInfo.setServiceInstance(serviceInstance);
      deviceInfo.setDeviceStatusId(DeviceStatus.ID_ACTIVE);
      deviceInfo.setEffectiveDate(new Date());
      deviceInfo.setNetworkInfo(networkInfo);
      deviceInfo.save();
      return request;
    } catch (DeviceException e) {
      disconnectService(request);
      throw new NetworkRequestException(NetworkRequestType.ACTIVATION, e.getMessage(), e.getCause());
    }
  }

  /**
   * Disconnects the NetworkInfo of the request. This does NOT update the
   * DeviceInfo and should only be used as a helper method for disconnect
   * requests.
   * 
   * @param request
   * @throws NetworkDisconnectException
   */
  protected NetworkRequest disconnectService(NetworkRequest request) throws NetworkRequestException {
    NetworkInfo networkInfo = networkSystem.getNetworkInfo(request.getDeviceInfo().getDeviceValue(), null);
    disconnect(networkInfo);
    return request;
  }

  /**
   * Deactivates the NetworkInfo of the request and updates the associated
   * DeviceInfo.
   * 
   * @param request
   * @return
   * @throws NetworkDeactivationException
   */
  protected NetworkRequest deactivateService(NetworkRequest request) throws NetworkRequestException {
    try {
      DeviceInfo deviceInfo = request.getDeviceInfo();
      NetworkInfo networkInfo = networkSystem.getNetworkInfo(deviceInfo.getDeviceValue(), null);
      deviceInfo.setNetworkInfo(networkInfo);
      ServiceInstance serviceInstance = billingSystem.getServiceInstance(deviceInfo);

      if (NetworkValidationUtil.checkDeviceOwnership(deviceInfo, networkInfo, serviceInstance)) {
        disconnect(networkInfo);
        deviceInfo.setDeviceStatusId(DeviceStatus.ID_RELEASED_REACTIVATEABLE);
        deviceInfo.setEffectiveDate(new Date());
        deviceInfo.setNetworkInfo(networkInfo);
        deviceInfo.setServiceInstance(serviceInstance);
        deviceInfo.save();
        return request;
      } else {
        // this should not occur as an exception should be thrown if the user
        // does not own the device
        return null;
      }
    } catch (DeviceException e) {
      String msg = "Could not update DeviceInfo. Msg: {0}";
      throw new NetworkRequestException(NetworkRequestType.DEACTIVATION, e.getCause(), msg, e.getMessage());
    } catch (ESNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.DEACTIVATION, e.getMessage(), e.getCause());
    } catch (MDNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.DEACTIVATION, e.getMessage(), e.getCause());
    }
  }

  /**
   * This allows full activation with only a DeviceInfo object by reserving a
   * NetworkInfo, activating it and binding it to the given DeviceInfo.
   * 
   * @param deviceInfo
   * @throws NetworkActivationException
   * @throws NetworkDisconnectException
   */
  protected NetworkRequest reserveAndActivate(NetworkRequest request) throws NetworkRequestException {
    DeviceInfo deviceInfo = request.getDeviceInfo();
    NetworkInfo networkInfo = networkSystem.reserveMDN(null, null, null);
    NetworkServiceUtil.bindNetworkInfoToDeviceInfo(deviceInfo, networkInfo);
    activate(networkInfo);
    try {
      ServiceInstance serviceInstance = new ServiceInstance();
      serviceInstance.setExternalId(networkInfo.getMdn());
      serviceInstance.setExternalIdType(3); // TODO move values to an enum
      deviceInfo.setDeviceStatusId(DeviceStatus.ID_ACTIVE);
      deviceInfo.setEffectiveDate(new Date());
      deviceInfo.setNetworkInfo(networkInfo);
      deviceInfo.setServiceInstance(serviceInstance);
      deviceInfo.save();
      // called save twice to update status id - needs to
      // implemented using hibernate
      deviceInfo.save();
      return request;
    } catch (DeviceException e) {
      disconnect(networkInfo);
      String msg = "Could not update DeviceInfo. Msg: {0}";
      throw new NetworkRequestException(NetworkRequestType.RESERVE_AND_ACTIVATE, e.getCause(), msg, e.getCause());
    }
  }

  /**
   * Restores the NetworkInfo of the request and updates the associated
   * DeviceInfo.
   * 
   * @param request
   * @throws NetworkStatusException
   * @throws NetworkRestoreException
   */
  protected NetworkRequest restoreService(NetworkRequest request) throws NetworkRequestException, NetworkStatusException {
    try {
      DeviceInfo deviceInfo = request.getDeviceInfo();
      NetworkInfo networkInfo = networkSystem.getNetworkInfo(deviceInfo.getDeviceValue(), null);
      deviceInfo.setNetworkInfo(networkInfo);
      ServiceInstance serviceInstance = billingSystem.getServiceInstance(deviceInfo);
      if (NetworkValidationUtil.checkDeviceOwnership(deviceInfo, networkInfo, serviceInstance)) {
        restore(networkInfo);
        // TODO Service instance should be updated by POST PROCESSOR
        // billingSystem.updateServiceInstanceStatus(serviceInstance,
        // BillingSystem.SERVICE_INSTANCE_RESTORED);
        deviceInfo.setDeviceStatusId(DeviceStatus.ID_ACTIVE);
        deviceInfo.setNetworkInfo(networkInfo);
        deviceInfo.setServiceInstance(serviceInstance);
        deviceInfo.save();
        return request;
      } else {
        // this should not occur as an exception should be thrown if the user
        // does not own the device
        return null;
      }
    } catch (DeviceException e) {
      String msg = "Could not update DeviceInfo. Msg: {0}";
      throw new NetworkRequestException(NetworkRequestType.RESTORE, e.getCause(), msg, e.getCause());
    } catch (ESNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.RESTORE, e.getMessage(), e.getCause());
    } catch (MDNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.RESTORE, e.getMessage(), e.getCause());
    }
  }

  /**
   * Suspends the NetworkInfo of the request and updates the associated
   * DeviceInfo.
   * 
   * @param request
   * @throws NetworkRequestException
   * @throws NetworkStatusException
   */
  protected NetworkRequest suspendService(NetworkRequest request) throws NetworkRequestException, NetworkStatusException {
    try {
      DeviceInfo deviceInfo = request.getDeviceInfo();
      NetworkInfo networkInfo = networkSystem.getNetworkInfo(deviceInfo.getDeviceValue(), null);
      deviceInfo.setNetworkInfo(networkInfo);
      ServiceInstance serviceInstance = billingSystem.getServiceInstance(deviceInfo);
      if (NetworkValidationUtil.checkDeviceOwnership(deviceInfo, networkInfo, serviceInstance)) {
        suspend(networkInfo);
        // TODO Service instance should be updated by POST PROCESSOR
        // billingSystem.updateServiceInstanceStatus(serviceInstance,
        // BillingSystem.SERVICE_INSTANCE_HOTLINED);
        deviceInfo.setDeviceStatusId(DeviceStatus.ID_RELEASED_SYSTEM_REACTIVATE);
        deviceInfo.setNetworkInfo(networkInfo);
        deviceInfo.setServiceInstance(serviceInstance);
        deviceInfo.save();
        return request;
      } else {
        // this should not occur as an exception should be thrown if the user
        // does not own the device
        return null;
      }
    } catch (DeviceException e) {
      String msg = "Could not update DeviceInfo. Msg: {0}";
      throw new NetworkRequestException(NetworkRequestType.SUSPEND, e.getCause(), msg, e.getCause());
    } catch (ESNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.SUSPEND, e.getMessage(), e.getCause());
    } catch (MDNMismatchException e) {
      throw new NetworkRequestException(NetworkRequestType.SUSPEND, e.getMessage(), e.getCause());
    }
  }

  /**
   * Reserves and activates the given Request and rebuilds the ServiceInstance
   * based on their last usage date in order to charge MRC properly. This is
   * deprecated as it should be broken into two requests: reserveAndActivate(*)
   * and create*ServiceInstance(*).
   * 
   * @param request
   * @throws NetworkActivationException
   * @throws NetworkDisconnectException
   * @Deprecated protected NetworkRequest reactivateService(NetworkRequest
   *             request) throws NetworkActivationException,
   *             NetworkDisconnectException { try { DeviceInfo deviceInfo =
   *             request.getDeviceInfo(); NetworkInfo networkInfo =
   *             networkSystem.getNetworkInfo(deviceInfo.getDeviceValue(),
   *             null); Account account =
   *             billingSystem.getAccountByAccountNo(request
   *             .getAccountNumber()); deviceInfo.setNetworkInfo(networkInfo);
   *             Customer customer = new Customer(request.getCustomerId()); if
   *             (NetworkValidationUtil.checkEsn(deviceInfo, networkInfo) &&
   *             NetworkValidationUtil.checkActive(networkInfo) &&
   *             NetworkValidationUtil.checkAccount(account)) {
   *             DeviceAssociation deviceAssociation =
   *             deviceService.getDeviceAssociation(customer, deviceInfo);
   *             reserveAndActivate(request); if
   *             (UsageValidationUtil.requiresMRC(request.getCustomerId(),
   *             request.getAccountNumber(), networkInfo.getMdn(),
   *             deviceAssociation.getInactiveDate())) { //
   *             accountService.createServiceInstance(request); } else { //
   *             accountService.createReinstallServiceInstance(request); }
   *             deviceService.updateDeviceAssociation(deviceInfo,
   *             DeviceStatus.ID_ACTIVE, networkInfo); return request; } else {
   *             // this should not occur as an exception should occur during
   *             validation return null; } } catch (NetworkException e) { throw
   *             new NetworkActivationException(e.getMessage(), e.getCause()); }
   *             catch (ESNMismatchException e) { throw new
   *             NetworkActivationException(e.getMessage(), e.getCause()); // }
   *             catch (ServiceInstanceException e) { // throw new
   *             NetworkActivationException(e.getMessage(), e.getCause()); }
   *             catch (UsageDetailAccessException e) { throw new
   *             NetworkActivationException(e.getMessage(), e.getCause()); }
   *             catch (DeviceException e) { throw new
   *             NetworkActivationException
   *             ("Could not update DeviceInfo. Message: " + e.getMessage(),
   *             e.getCause()); } }
   */

}

package com.tscp.mvne.util.audit;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class AuditTester {
  private static AuditService auditService = new AuditService();

  @WebMethod
  public Status getStatus(int custId, int accountNo) {
    Status status = auditService.getStatus(custId, accountNo);
    return status;
  }

}

package com.tscp.mvne.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.base.Preconditions;

@SuppressWarnings("unchecked")
public abstract class AbstractHibernateDao<T extends Serializable> implements AbstractHibernateDaoModel<T> {
  private static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
  private Class<T> clazz;

  @Override
  public void setClazz(final Class<T> clazzToSet) {
    this.clazz = clazzToSet;
  }

  @Override
  public T getById(final Integer id) {
    Preconditions.checkArgument(id != null);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    T result = (T) session.get(this.clazz, id);
    tx.commit();
    return result;
  }

  @Override
  public List<T> getAll() {
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    List<T> results = session.createQuery("from " + this.clazz.getName()).list();
    tx.commit();
    return results;
  }

  @Override
  public void persist(final T entity) {
    Preconditions.checkNotNull(entity);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    session.persist(entity);
    tx.commit();
  }

  @Override
  public T merge(final T entity) {
    Preconditions.checkNotNull(entity);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    T result = (T) session.merge(entity);
    tx.commit();
    return result;
  }

  @Override
  public Serializable save(final T entity) {
    Preconditions.checkNotNull(entity);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    Serializable result = session.save(entity);
    tx.commit();
    return result;
  }

  @Override
  public void update(final T entity) {
    Preconditions.checkNotNull(entity);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    session.update(entity);
    tx.commit();
  }

  @Override
  public void delete(final T entity) {
    Preconditions.checkNotNull(entity);
    Session session = getCurrentSession();
    Transaction tx = session.beginTransaction();
    session.delete(entity);
    tx.commit();
  }

  @Override
  public void deleteById(final Integer entityId) {
    final T entity = this.getById(entityId);
    Preconditions.checkState(entity != null);
    this.delete(entity);
  }

  protected final Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  protected final boolean isUniqueResult(Collection<T> results) {
    return results != null && results.size() == 1;
  }

  protected final T getUniqueResult(List<T> results) {
    return results.get(0);
  }

  protected final String wildcard(String param) {
    return "%" + param + "%";
  }
}
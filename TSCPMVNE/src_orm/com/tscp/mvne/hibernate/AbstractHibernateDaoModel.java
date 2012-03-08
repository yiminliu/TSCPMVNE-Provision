package com.tscp.mvne.hibernate;

import java.io.Serializable;
import java.util.Collection;

public interface AbstractHibernateDaoModel<T extends Serializable> {

  public abstract void setClazz(final Class<T> clazzToSet);

  public abstract T getById(final Integer id);

  public abstract Collection<T> getAll();

  public abstract void persist(final T entity);

  public abstract T merge(final T entity);

  public abstract Serializable save(final T entity);

  public abstract void update(final T entity);

  public abstract void delete(final T entity);

  public abstract void deleteById(final Integer entityId);

}
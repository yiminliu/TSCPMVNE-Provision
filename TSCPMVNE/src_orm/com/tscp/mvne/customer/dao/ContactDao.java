package com.tscp.mvne.customer.dao;

import com.tscp.mvne.customer.contact.Contact;
import com.tscp.mvne.hibernate.AbstractHibernateDao;

public class ContactDao extends AbstractHibernateDao<Contact> {

  public ContactDao() {
    setClazz(Contact.class);
  }

}

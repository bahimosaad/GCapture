/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gdit.capture.entity;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

/**
 *
 * @author ehab
 */
public class FieldHome {
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public FieldHome() {
    }

    public static Session getSession() {
        Session session = (Session) FieldHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            FieldHome.session.set(session);
        }
        return session;
    }

    public long persist(Field transientInstance) {
        try {
            begin();
            long c = (Long) getSession().save(transientInstance);
            commit();
            return transientInstance.getId();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }
    public long update(Field transientInstance) {
        try {
            begin();
            getSession().update(transientInstance);
            commit();
            return transientInstance.getId();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }
    public void delete(Field transientInstance) {
        try {
            begin();
            getSession().delete(transientInstance);
            commit();
        } catch (RuntimeException re) {
            re.printStackTrace();
            throw re;
        }
    }

    private void begin() {
        getSession().beginTransaction();
    }

    private void commit() {
        getSession().getTransaction().commit();
    }

    public void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
        }

        try {
            getSession().close();
        } catch (HibernateException e) {
        }
        FieldHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        FieldHome.session.set(null);
    }

    public List<Field> listAllFields(){
        try {
            begin();
            List instances = getSession().createQuery("select f from Field f").list();
            return instances;

        } catch (RuntimeException re) {
       //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

}

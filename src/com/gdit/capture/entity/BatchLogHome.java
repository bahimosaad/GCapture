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
 * @author Bahi
 */
public class BatchLogHome {

    public static Session getSession() {
        Session session = (Session) BatchLogHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            BatchLogHome.session.set(session);
        }
        return session;
    }

    public void persist(BatchLog transientInstance) {
        log.log(Level.WARNING, "persisting BatchLog instance");
        try {
            begin();
            getSession().persist(transientInstance);
            log.log(Level.WARNING, "persist successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<BatchLog> getAllBatchLog() {
        Query q = getSession().createQuery("from BatchLog");
        List<BatchLog> list = (List<BatchLog>) q.list();
        return list;
    }

    public void attachDirty(BatchLog instance) {
        log.log(Level.WARNING, "attaching dirty BatchLog instance");
        try {
            begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(BatchLog instance) {
        log.log(Level.WARNING, "attaching clean BatchLog instance");
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(BatchLog persistentInstance) {
        log.log(Level.WARNING, "deleting BatchLog instance");
        try {
            begin();
            getSession().delete(persistentInstance);
            log.log(Level.WARNING, "delete successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "delete failed", re);
            throw re;
        }
    }

    public void begin() {
        getSession().beginTransaction();
    }

    public void commit() {
        getSession().getTransaction().commit();
    }

    public void rollback() {
        try {
            getSession().getTransaction().rollback();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot rollback", e);
        }

        try {
            getSession().close();
        } catch (HibernateException e) {
            log.log(Level.WARNING, "Cannot close", e);
        }
        BatchLogHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        BatchLogHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public BatchLog merge(BatchLog detachedInstance) {
        log.log(Level.WARNING, "merging BatchLog instance");
        try {
            BatchLog result = (BatchLog) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public BatchLog findById(long id) {
        log.log(Level.WARNING, "getting BatchLog instance with id: " + id);
        try {
            begin();
            BatchLog instance = (BatchLog) getSession().get("com.gdit.capture.entity.BatchLog", id);
            if (instance == null) {
                log.log(Level.WARNING, "get successful, no instance found");
            } else {
                log.log(Level.WARNING, "get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public BatchLog findUnlockBatch(String compName) {
        try {
            begin();
            List<BatchLog> instances = getSession().createQuery("select b from BatchLog b where b.locked = false "
                    + "and b.computerName = :compName "
                    + "order by b.batchId")
                    .setParameter("compName", compName)
                    .list();
            if (instances != null && instances.size() > 0) {
                BatchLog instance = instances.get(0);
                instance.setLocked(true);
                getSession().merge(instance);
                commit();
                return instance;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public BatchLog findBarcodedBatch() {
        List<BatchLog> instances = getSession().createQuery("select b from BatchLog b where b.locked = false and b.status = 2").list();
        if (instances != null && instances.size() > 0) {
            return instances.get(0);
        } else {
            return null;
        }
    }

    public List findByExample(BatchLog instance) {
        log.log(Level.WARNING, "finding BatchLog instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.BatchLog").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }
}

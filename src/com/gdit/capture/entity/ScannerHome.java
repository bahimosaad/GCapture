package com.gdit.capture.entity;
// Generated 13/10/2010 04:34:04 � by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Category.
 * @see com.gdit.capture.entity.Category
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

public class ScannerHome {

    public ScannerHome() {
    }

    public static Session getSession() {
        Session session = (Session) ScannerHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            ScannerHome.session.set(session);
        }
        return session;
    }

    public void persist(Scanner transientInstance) {
        log.log(Level.WARNING, "persisting Category instance");
        try {
            begin();
            getSession().persist(transientInstance);
            commit();
            log.log(Level.WARNING, "persist successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<Scanner> getAllCaps() {
        Query q = getSession().createQuery("from Scanner");
        List<Scanner> list = (List<Scanner>) q.list();
        return list;
    }

    public void attachDirty(Scanner instance) {
        log.log(Level.WARNING, "attaching dirty Scanner instance");
        try {
            begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(Server instance) {
        log.log(Level.WARNING, "attaching clean Scanner instance");
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(Server persistentInstance) {
        log.log(Level.WARNING, "deleting Category instance");
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
        ScannerHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        ScannerHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Scanner merge(Scanner detachedInstance) {
        log.log(Level.WARNING, "merging Category instance");
        try {
            Scanner result = (Scanner) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public Scanner findById(long id) {
        log.log(Level.WARNING, "getting Server instance with id: " + id);
        try {
            begin();
            Scanner instance = (Scanner) getSession().get("com.gdit.capture.entity.Server", id);
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

    public Scanner findByCapName(String capName) {
        //log.log(Level.WARNING, "getting Server instance with id: " + id);
        try {
            begin();
            List<Scanner> instances = (List<Scanner>) getSession()
                    .createQuery("select s from Scanner s where s.capName = :capName")
                    .setParameter("capName", capName)
                    .list();
            if (instances == null || instances.size() > 0) {
                return null;
              //  log.log(Level.WARNING, "get successful, no instance found");
            } else {
                return instances.get(0);
            }
            //return instance;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List findByExample(Scanner instance) {
        log.log(Level.WARNING, "finding Server instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.Scanner").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }
}

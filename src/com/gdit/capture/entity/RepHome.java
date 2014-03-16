package com.gdit.capture.entity;
// Generated Aug 8, 2010 4:43:53 AM by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Rep.
 * @see com.gdit.capture.entity.Rep
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

public class RepHome {

    /**
     * Dependency injecting constructor.
     */
    public RepHome() {
    }

    public static Session getSession() {
        Session session = (Session) RepHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            RepHome.session.set(session);
        }
        return session;
    }

    public void persist(Rep transientInstance) {
        log.log(Level.WARNING, "persisting Rep instance");
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

    public List<Rep> getAllRep() {
        Query q = getSession().createQuery("from Rep");
        List<Rep> list = (List<Rep>) q.list();
        return list;
    }
    public List<Category>   getRepCategories(Rep rep) {
        Query q = getSession().createQuery("select g from Category g where g.rep = :rep ").setParameter("rep", rep);
        List<Category> list = (List<Category>) q.list();
        return list;
    }

    public void attachDirty(Rep instance) {
        log.log(Level.WARNING, "attaching dirty Rep instance");
        try {
            begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(Rep instance) {
        log.log(Level.WARNING, "attaching clean Rep instance");
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(Rep persistentInstance) {
        log.log(Level.WARNING, "deleting Rep instance");
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
        RepHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        RepHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public Rep merge(Rep detachedInstance) {
        log.log(Level.WARNING, "merging Rep instance");
        try {
            Rep result = (Rep) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public Rep findById(short id) {
        log.log(Level.WARNING, "getting Rep instance with id: " + id);
        try {
            begin();
            Rep instance = (Rep) getSession().get("com.gdit.capture.entity.Rep", id);
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

    public List findByExample(Rep instance) {
        log.log(Level.WARNING, "finding Rep instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.Rep").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }

    public Rep findRepByName(String name){
      List<Rep> reps =   getSession().createQuery("select r From Rep r where r.name = :name")
                .setParameter("name",name)
                .list();
      if(reps!=null && reps.size() > 0)
          return reps.get(0);
      else
          return null;
    }
}

package com.gdit.capture.entity;
// Generated 23/10/2010 01:01:59  by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class UsersGroups.
 * @see com.gdit.capture.entity.UsersGroups
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

public class UsersGroupsHome {

    /**
     * Dependency injecting constructor.
     */
    public UsersGroupsHome() {
    }

    public static Session getSession() {
        Session session = (Session) UsersGroupsHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            UsersGroupsHome.session.set(session);
        }
        return session;
    }

    public void persist(UsersGroups transientInstance) {
        log.log(Level.WARNING, "persisting UsersGroups instance");
        try {
            begin();
            getSession().persist(transientInstance);
            log.log(Level.WARNING, "persist successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<UsersGroups> getAllUsersGroups() {
        Query q = getSession().createQuery("from UsersGroups");
        List<UsersGroups> list = (List<UsersGroups>) q.list();
        return list;
    }

    public void attachDirty(UsersGroups instance) {
        log.log(Level.WARNING, "attaching dirty UsersGroups instance");
        try {
            begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(UsersGroups instance) {
        log.log(Level.WARNING, "attaching clean UsersGroups instance");
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(UsersGroups persistentInstance) {
        log.log(Level.WARNING, "deleting UsersGroups instance");
        try {
            begin();
            getSession().delete(persistentInstance);
            log.log(Level.WARNING, "delete successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "delete failed", re);
            throw re;
        }
    }

    public void deleteUserGroups(Users user) {
        try {
            begin();
             getSession().createQuery("delete from UsersGroups ug where ug.users = :user")
                     .setParameter("user",user).executeUpdate();
             commit();
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
        UsersGroupsHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        UsersGroupsHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public UsersGroups merge(UsersGroups detachedInstance) {
        log.log(Level.WARNING, "merging UsersGroups instance");
        try {
            UsersGroups result = (UsersGroups) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public UsersGroups findById(com.gdit.capture.entity.UsersGroupsId id) {
        log.log(Level.WARNING, "getting UsersGroups instance with id: " + id);
        try {
            begin();
            UsersGroups instance = (UsersGroups) getSession().get("com.gdit.capture.entity.UsersGroups", id);
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

    public List findByExample(UsersGroups instance) {
        log.log(Level.WARNING, "finding UsersGroups instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.UsersGroups").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }
}

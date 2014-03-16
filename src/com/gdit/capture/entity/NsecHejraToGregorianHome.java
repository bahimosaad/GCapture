package com.gdit.capture.entity;
// Generated 15/12/2010 08:54:04   by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class NsecHejraToGregorian.
 * @see com.gdit.capture.entity.NsecHejraToGregorian
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

public class NsecHejraToGregorianHome {

    /**
     * Dependency injecting constructor.
     */
    public NsecHejraToGregorianHome() {
    }

    public static Session getSession() {
        Session session = (Session) NsecHejraToGregorianHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            NsecHejraToGregorianHome.session.set(session);
        }
        return session;
    }

    public void persist(NsecHejraToGregorian transientInstance) {

        try {
            begin();
            getSession().persist(transientInstance);


        } catch (RuntimeException re) {

            throw re;
        }
    }

    public List<NsecHejraToGregorian> getAllNsecHejraToGregorian() {
        Query q = getSession().createQuery("from NsecHejraToGregorian");
        List<NsecHejraToGregorian> list = (List<NsecHejraToGregorian>) q.list();
        return list;
    }

    public void attachDirty(NsecHejraToGregorian instance) {

        try {
            begin();
            getSession().saveOrUpdate(instance);

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public boolean checkHejriDate(int day, int month, int year) {
        try {
            List<NsecHejraToGregorian> instances = getSession().createQuery("select n from NsecHejraToGregorian n where n.id.hejraDd = :day "
                    + " AND n.id.hejraMm = :month AND n.id.hejraYyyy = :year").setParameter("day", day).setParameter("month", month).setParameter("year", year).list();
            if (instances == null || instances.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public void attachClean(NsecHejraToGregorian instance) {

        try {
            begin();
            getSession().lock(instance, LockMode.NONE);

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public void delete(NsecHejraToGregorian persistentInstance) {

        try {
            begin();
            getSession().delete(persistentInstance);

        } catch (RuntimeException re) {

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
            e.printStackTrace();
        }

        try {
            getSession().close();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        NsecHejraToGregorianHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        NsecHejraToGregorianHome.session.set(null);
    }
    //private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public NsecHejraToGregorian merge(NsecHejraToGregorian detachedInstance) {

        try {
            NsecHejraToGregorian result = (NsecHejraToGregorian) sessionFactory.getCurrentSession().merge(detachedInstance);

            return result;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public NsecHejraToGregorian findById(com.gdit.capture.entity.NsecHejraToGregorianId id) {

        try {
            begin();
            NsecHejraToGregorian instance = (NsecHejraToGregorian) getSession().get("com.gdit.capture.entity.NsecHejraToGregorian", id);
            if (instance == null) {
            } else {
            }
            return instance;
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public List findByExample(NsecHejraToGregorian instance) {

        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.NsecHejraToGregorian").add(Example.create(instance)).list();

            return results;
        } catch (RuntimeException re) {

            throw re;
        }
    }
}

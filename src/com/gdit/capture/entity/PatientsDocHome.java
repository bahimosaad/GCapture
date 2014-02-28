package com.gdit.capture.entity;
// Generated 14/12/2010 07:03:58  by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class PatientsDoc.
 * @see com.gdit.capture.entity.PatientsDoc
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

public class PatientsDocHome {

    /**
     * Dependency injecting constructor.
     */
    public PatientsDocHome() {
    }

    public static Session getSession() {
        Session session = (Session) PatientsDocHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            PatientsDocHome.session.set(session);
        }
        return session;
    }

    public void persist(PatientsDoc transientInstance) {

        try {
            begin();
            getSession().persist(transientInstance);
            commit();

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public List<PatientsDoc> getAllPatientsDoc() {
        Query q = getSession().createQuery("from PatientsDoc");
        List<PatientsDoc> list = (List<PatientsDoc>) q.list();
        return list;
    }

    public void attachDirty(PatientsDoc instance) {

        try {
            begin();
            getSession().saveOrUpdate(instance);
            //commit();
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public void attachClean(PatientsDoc instance) {

        try {
            begin();
            getSession().update(instance);

        } catch (RuntimeException re) {

            throw re;
        }
    }

    public void delete(PatientsDoc persistentInstance) {

        try {
            begin();
            getSession().delete(persistentInstance);

        } catch (RuntimeException re) {
            log.log(Level.WARNING, "delete failed", re);
            throw re;
        }
    }

    public PatientsDoc findByById(String id) {
        try {
            begin();
            List<PatientsDoc> instances = getSession().createQuery("select c from PatientsDoc c where c.id = :id").setParameter("id", Long.valueOf(id)).list();
            return instances.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
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
        PatientsDocHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        PatientsDocHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public PatientsDoc merge(PatientsDoc detachedInstance) {
        log.log(Level.WARNING, "merging PatientsDoc instance");
        try {
            PatientsDoc result = (PatientsDoc) getSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public PatientsDoc findById(long id) {
        log.log(Level.WARNING, "getting PatientsDoc instance with id: " + id);
        try {
            begin();
            PatientsDoc instance = (PatientsDoc) getSession().get("com.gdit.capture.entity.PatientsDoc", id);
            if (instance == null) {
                log.log(Level.WARNING, "get successful, no instance found");
            } else {
                log.log(Level.WARNING, "get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "get failed", re);
            return null;
            // throw re;
        }
    }

    public List findByExample(PatientsDoc instance) {
        log.log(Level.WARNING, "finding PatientsDoc instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.PatientsDoc").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }

    public List<PatientsDoc> getDocsByUser(Integer userId) {
        try {
            List<PatientsDoc> instances = getSession().createQuery("select p from PatientsDoc p where p.userId = :user").setParameter("user", userId).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List getUserStatiscs(Integer userId,String date) {
        return getSession().createSQLQuery("select user_name , to_Char(created_date,'dd/MM/YYYY') , count(1) "
                + "from patients_doc p , users u where p.user_id = u.id and( p.user_id = :userId or :userId = 0) "
                + "and (:date = '0' or to_Char(created_date,'dd/MM/yyyy') = :date    ) "
                + "group by user_name , to_Char(created_date,'dd/MM/YYYY') "
                + "order by to_Char(created_date,'dd/MM/YYYY') desc ")
                .setParameter("userId", userId)
                .setParameter("date",date)
                .list();
    }
}

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
public class UsersAuditHome {

    public static Session getSession() {
        Session session = (Session) UsersAuditHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            UsersAuditHome.session.set(session);
        }
        return session;
    }

    public void persist(UsersAudit transientInstance) {

        try {
            begin();
            getSession().persist(transientInstance);
            commit();
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<UsersAudit> getAllUsersAudit() {
        Query q = getSession().createQuery("from UsersAudit");
        List<UsersAudit> list = (List<UsersAudit>) q.list();
        return list;
    }

    public void attachDirty(Computers instance) {

        try {
            begin();
            getSession().saveOrUpdate(instance);
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(UsersAudit instance) {
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(UsersAuditHome persistentInstance) {
        try {
            begin();
            getSession().delete(persistentInstance);
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
        UsersAuditHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        UsersAuditHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration()
            .configure().buildSessionFactory();

    public UsersAudit merge(Computers detachedInstance) {
        try {
            UsersAudit result = (UsersAudit) getSession().merge(detachedInstance);
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public Computers findById(int id) {
        try {
            begin();
            Computers instance = (Computers) getSession()
                    .get("com.gdit.capture.entity.UsersAudit", id);
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

    public List findByExample(UsersAuditHome instance) {
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.UsersAudit")
                    .add(Example.create(instance))
                    .list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }

    public List getUserProductivity(Integer userId, String date, int moduleId) {
        return getSession().createSQLQuery("select t1.user_name,t1.d,b,p from "
                + "(select user_name , to_Char(audit_date,'dd/MM/YYYY') d , count(1) b "
                + "from Users_Audit p , users u where p.user_id = u.id  "
                + " and( p.user_id = :userId or :userId = 0) "
                + "and (:date = '0' or to_Char(audit_date,'dd/MM/yyyy') = :date )   "
                + "and action IN(1,2)  and p.module_id = :module    "
                + " and doc_id not in"
                + "("
                + "select distinct doc_id  from DOCUMENT_DATA "
                + "where field_val is null "
                + "group by doc_id , field_val  "
                + "having count(doc_id)=5) "
                + "group by user_name , to_Char(audit_date,'dd/MM/YYYY') "
                + "order by to_Char(audit_date,'dd/MM/YYYY') desc) t1 , "
                + " (select user_name , to_Char(audit_date,'dd/MM/YYYY') d , count(1) p "
                + "from Users_Audit p , users u,Capture_VW c"
                + " where p.user_id = u.id and c.B_ID = p.BATCH_ID "
                + " and( p.user_id = :userId or :userId = 0) "
                + "and (:date = '0' or to_Char(audit_date,'dd/MM/yyyy') = :date )   "
                + "and action IN(1,2)  and p.module_id = :module  "
                + "group by user_name , to_Char(audit_date,'dd/MM/YYYY') "
                + "order by to_Char(audit_date,'dd/MM/YYYY') desc) t2"
                + " where t1.user_name = t2.user_name and t1.d = t2.d  ")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .setParameter("module", moduleId)
                .list();
    }

    public List getUserPagesProductivity(Integer userId, String date, int moduleId) {
        return getSession().createSQLQuery("select user_name , to_Char(audit_date,'dd/MM/YYYY') , count(1) "
                + "from Users_Audit p , users u,Capture_VW c"
                + " where p.user_id = u.id and c.B_ID = p.BATCH_ID "
                + " and( p.user_id = :userId1 or :userId1 = 0) "
                + "and (:date1 = '0' or to_Char(audit_date,'dd/MM/yyyy') = :date1 )   "
                + "and action IN(1,2)  and p.module_id = :module  "
                + "group by user_name , to_Char(audit_date,'dd/MM/YYYY') "
                + "order by to_Char(audit_date,'dd/MM/YYYY') desc ")
                .setParameter("userId1", userId)
                .setParameter("date1", date)
                .setParameter("module", moduleId)
                .list();
    }
}

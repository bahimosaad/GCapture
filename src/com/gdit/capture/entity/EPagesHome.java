package com.gdit.capture.entity;
// Generated 13/10/2010 04:34:04 � by Hibernate Tools 3.2.0.beta7

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Category.
 *
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

public class EPagesHome {

    public EPagesHome() {
    }

    public static Session getSession() {
        Session session = (Session) EPagesHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            EPagesHome.session.set(session);
        }
        return session;
    }

    public void persist(EPages transientInstance) {
       // log.log(Level.WARNING, "persisting EPages instance");
        try {
            begin();
            getSession().persist(transientInstance);
            commit();
          //  log.log(Level.WARNING, "persist successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<EPages> getAllCaptureDocument() {
        Query q = getSession().createQuery("from EPages");
        List<EPages> list = (List<EPages>) q.list();
        return list;
    }

    public void attachDirty(EPages instance) {
      //  log.log(Level.WARNING, "attaching dirty EPages instance");
        try {
            begin();
            getSession().saveOrUpdate(instance);
            commit();
         //   log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(EPages instance) {
        log.log(Level.WARNING, "attaching clean EPages instance");
        try {
            begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(EPages persistentInstance) {
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
        EPagesHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        EPagesHome.session.set(null);
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public EPages merge(EPages detachedInstance) {
        log.log(Level.WARNING, "merging EPages instance");
        try {
            EPages result = (EPages) sessionFactory.getCurrentSession().merge(detachedInstance);
            log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public EPages findById(long id) {
        // log.log(Level.WARNING, "getting EPages instance with id: " + id);
        try {
            begin();
            EPages instance = (EPages) getSession().get("com.gdit.capture.entity.EPages", id);
            if (instance == null) {
                //      log.log(Level.WARNING, "get successful, no instance found");
            } else {
                //    log.log(Level.WARNING, "get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            // log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List findByExample(EPages instance) {
        log.log(Level.WARNING, "finding EPages instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.Category").add(Example.create(instance)).list();
            log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }

//    public List<EPages> findDocs(String jrnlType,int fromNo,int toNo,String fromDate,String toDate){
//         try{
//            begin();
//          return   getSession().createQuery("select c from EPages c where c.jrnlType = :jrnlType and "
//                    + "c.lineNo between :fromNo and :toNo and c.jrnlDate between :fromDate and :toDate ")
//                    .setParameter("jrnlType", jrnlType)
//                    .setParameter("fromNo", fromNo)
//                    .setParameter("toNo",toNo)
//                    .setParameter("fromDate", fromDate)
//                    .setParameter("toDate",toDate)
//                    .list();
//          
//        }catch(Exception ex){
//            ex.printStackTrace();
//            return null;
//        }
//    }
    public List<EPages> findDocs(String jrnlType, int fromNo, int toNo, String fromDate, String toDate) {
        try {
            begin();
            return getSession().createSQLQuery("select * from CAPTURE.CAPTURE_DOCUMENT where jrnl_type =:jrnlType"
                    + " and line_no between :fromNo and :toNo "
                    + "and TO_DATE(jrnl_date,'DD-MM-YYYY') between "
                    + "to_date(:fromDate,'DD-MM-YYYY') and to_date(:toDate,'DD-MM-YYYY') ").setParameter("jrnlType", jrnlType).setParameter("fromNo", fromNo).setParameter("toNo", toNo).setParameter("fromDate", fromDate).setParameter("toDate", toDate).list();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<EPages> findDocs(long lineNo, String yer, String date) {
        try {
            begin();
            return getSession().createQuery("select d from EPages d where"
                    + "   d.lineNo =:lineNo"
                    + " and  d.jrnlDate  =  :date "
                    + " and d.yer = :yer ").setParameter("date", date).setParameter("yer", yer).setParameter("lineNo", lineNo).list();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void uodateDocs(String jrnlType, int fromNo, int toNo, String fromDate, String toDate, String fileNo, String boxNo, String corridorNo, String columnNo) {
        begin();
        getSession().createQuery("update  CaptureDocuments c "
                + "set c.fileNo = :fileNo , c.boxNo = :boxNo ,c.corridorNo = :corridorNo ,c.columnNo = :columnNo "
                + "where c.jrnlType =:jrnlType"
                + " and c.lineNo between :fromNo and :toNo "
                + "and TO_DATE(c.jrnlDate,'DD-MM-YYYY') between "
                + "to_date(:fromDate,'DD-MM-YYYY') and to_date(:toDate,'DD-MM-YYYY') ").setParameter("jrnlType", jrnlType).setParameter("fromNo", fromNo).setParameter("toNo", toNo).setParameter("fromDate", fromDate).setParameter("toDate", toDate).setParameter("fileNo", fileNo).setParameter("boxNo", boxNo).setParameter("corridorNo", corridorNo).setParameter("columnNo", columnNo).executeUpdate();

    }
}

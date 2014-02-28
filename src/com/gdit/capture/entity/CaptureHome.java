package com.gdit.capture.entity;
// Generated Aug 8, 2010 4:43:53 AM by Hibernate Tools 3.2.0.beta7

import com.gdit.capture.model.CaptureStatus;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Capture.
 *
 * @see com.gdit.capture.entity.Capture
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class CaptureHome {

    /**
     * Dependency injecting constructor.
     */
    public CaptureHome() {
//       begin();
    }

    public void persist(Capture transientInstance) {
        //  log.log(Level.WARNING, "persisting Capture instance");
        try {
//            begin();

            //  transientInstance.setId(getMaxID() + 1);
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();

            session.save(transientInstance);
            tx.commit();
            //      log.log(Level.WARNING, "persist successful");

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public long persistImage(Capture transientInstance) {
        // log.log(Level.WARNING, "persisting Capture instance");
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();

            Capture c = (Capture) session.save(transientInstance);

            tx.commit();
            return c.getId();
            //    log.log(Level.WARNING, "persist successful");

        } catch (RuntimeException re) {
            //  log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<String> findByBarcode(String barcode, int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
               Transaction tx = session.beginTransaction();
               
            List<String> instances = session.createQuery("select c.capture.name from Capture c where c.barcode = :barcode"
                    + " and c.catgeory = :category and c.status = :status")
                    .setParameter("barcode", barcode)
                    .setParameter("category", category)
                    .setParameter("status", status) //                    .setParameter("category", category)
                    //                    .setParameter("locked",false)
                    .list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//    public List<Capture> findByBarcode(String barcode, int status, Category category) {
//        try {
//            begin();
//            List<String> instances = getSession().createQuery("select c.name from Capture c where c.barcode = :barcode"
//                    + " and c.status = :status").setParameter("barcode", barcode).setParameter("status", status) //                    .setParameter("category", category)
//                    //                    .setParameter("locked",false)
//                    .list();
//            return instances;
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return null;
//        }
//    }

    public void deleteImage(Capture parent, String name) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
               
            session.createQuery("delete from Capture c where c.capture = :parent AND c.name = :name").setParameter("parent", parent).setParameter("name", name).executeUpdate();
            tx.commit();
            //  log.log(Level.WARNING, "persist successful");

        } catch (RuntimeException re) {
            // log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public void delete(long id) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.createQuery("delete from Capture c where c.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
           tx.commit();
            //  log.log(Level.WARNING, "persist successful");

        } catch (RuntimeException re) {
            // log.log(Level.WARNING, "persist failed", re);
            throw re;
        }
    }

    public List<Capture> getQAGrand() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 AND c.status = 3").list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getBatchesByStatus(int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 "
                    + "AND c.status = :status "
                    //         + "AND c.category = :category "
                    + "AND c.locked = :locked  order by c.id"
                    + "").setParameter("status", status) //      .setParameter("category", category)
                    .setParameter("locked", false).list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getUnBlankedbatches(Rep rep, int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 "
                    + "AND c.status = :status and c.deleted = false "
                    + "AND c.rep = :rep "
                    + "AND c.barcoded = false  order by c.id"
                    + "").setParameter("status", status) //      .setParameter("category", category)
                    .setParameter("rep", rep).list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }
    public Capture getSingleBlankBatch(Rep rep, int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 "
                    + "AND c.status = :status and c.deleted = false "
                    + "AND c.rep = :rep and c.locked = false "
                    + "AND c.barcoded = false   "
                    + "").setParameter("status", status) //      .setParameter("category", category)
                    .setParameter("rep", rep).list();
            
            return instances.size() > 0 ? (Capture) instances.get(0):null;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<String> getBatcheNamesByStatus(int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select  c.name from Capture c where c.type = 1 "
                    + "AND c.status = :status "
                    + "AND c.category = :category "
                    + "AND c.locked = :locked and c.barcoded =true  order by c.id"
                    + "")
                    .setParameter("status", status) //      .setParameter("category", category)
                    .setParameter("category", category)
                    .setParameter("locked", false).list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getUnScannedBatches() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select b from Capture b where b IN ( "
                    + "select d.capture from Capture d where d IN ( "
                    + "select p.capture from Capture p where p.refuseNote <> :note )) order by b.id").setParameter("note", "Re Scan Page").list();

            return instances;
        } catch (RuntimeException ex) {
            throw ex;
        }
    }

    public void updateStatus(Capture parent) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.createQuery("update Capture c set c.status = :status where c.capture IN( select c1 from Capture c1 "
                    + " where c1.capture = :parent ) OR c.capture = :parent").setParameter("parent", parent).setParameter("status", parent.getStatus()).executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateDeleted(Capture parent) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.createQuery("update Capture c set c.deleted = :deleted where c.capture IN( select c1 from Capture c1 "
                    + " where c1.capture = :parent ) OR c.capture = :parent")
                    .setParameter("parent", parent)
                    .setParameter("deleted", parent.getDeleted())
                    .executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateLock(Capture parent) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.createQuery("update Capture c set c.locked = :locked where c.capture IN( select c1 from Capture c1 "
                    + " where c1.capture = :parent ) OR c.capture = :parent").setParameter("parent", parent).setParameter("locked", parent.getLocked()).executeUpdate();
            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Capture> getAllBatches(int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + "c.status = :status order by c.id ").setParameter("status", status).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getAllBatches(Category cat, int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + "c.status = :status and c.category = :category order by c.id ")
                    .setParameter("status", status)
                    .setParameter("category", cat)
                    .list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getLockedBatches(int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + " c.locked = true and c.status = :status order by c.id ").setParameter("status", status).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getAllBatches() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0  "
                    + " order by c.id ").list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUnSavedBatches() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.type = 1 and c.status > 1 and c.deleted = 0 and c.saved = false "
                    + " order by c.id ").list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUnSavedBatches(long from, long to) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.type = 1 and c.status > 1 and c.deleted = 0 and c.id between :from and :to "
                    + " order by c.id ")
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUnBarcoded() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.type = 1 and c.barcoded = false "
                    + "and c.deleted = 0 and c.status  > 1 and c.id IN (select d.capture.id from Capture d where d.type = 2 and  d.barcode is null "
                    + ")"
                    + " order by c.id ").list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUnBarcoded(Rep rep) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.type = 1 and c.rep = :rep and  c.barcoded = false "
                    + "and c.deleted = 0 and c.status  > 1 and c.id IN (select d.capture.id from Capture d where d.type = 2 and  d.barcode is null "
                    + ")"
                    + " order by c.id ")
                    .setParameter("rep", rep)
                    .list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUnBarcoded(long id) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.id > :id "
                    + "and c.deleted = 0  and c.id IN (select d.capture.id from Capture d where d.type = 2 and ( d.barcode is null "
                    + "or d.barcode = 'null' ))"
                    + " order by c.id ").setParameter("id", id).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getUnBarcodedNames() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<String> instances = session.createQuery("select c.id from Capture c where c.type = 1 "
                    + "and (barcode is null OR barcode <> 'NOTFOUND')  and c.deleted = 0  and c.id IN (select d.capture.id from Capture d where d.type = 2 and ( d.barcode is null "
                    + "or d.barcode = 'null' ))"
                    + " order by c.id ").list();
//        List<Capture> docs = new ArrayList<Capture>();
//        for(Capture instance:instances){
//            docs.add(instance.getCapture());
//        }
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getUnBarcodedBitchNames() {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<String> instances = session.createQuery("select c.name from Capture c where c.type = 1 "
                    + "and c.deleted = 0  and c.id IN (select d.capture.id from Capture d where d.type = 2 and ( d.barcode is null "
                    + "or d.barcode = 'null' ))"
                    + " order by c.id ").list();
//        List<Capture> docs = new ArrayList<Capture>();
//        for(Capture instance:instances){
//            docs.add(instance.getCapture());
//        }
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getUncopiedbatches(int status) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and "
                    + "c.deleted = 0 and c.copyStatus is null and "
                    + "c.status = :status order by c.id ").setParameter("status", status).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCaptureState(Capture capture) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.update(capture);
            tx.commit();
        } catch (Exception e) {
        }
    }

    public int getMaxID() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        Query q = session.createQuery("select MAX(c.id) from Capture c");

        if (q.list().get(0) != null) {
            return (Integer) q.list().get(0);
        } else {
            return 0;
        }
    }

    public List<Capture> getAllCapture() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        Query q = session.createQuery("from Capture c order by c.id");
        List<Capture> list = (List<Capture>) q.list();
        return list;
    }

    public List<Capture> getAllTestCapture() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        Query q = session.createQuery("from Capture c WHERE   c.id  NOT LIKE c.name order by c.id");
        List<Capture> list = (List<Capture>) q.list();
        return list;
    }

    public void attachDirty(Capture instance) {
        //  log.log(Level.WARNING, "attaching dirty Capture instance");
        try {
//            begin();
//            session.saveOrUpdate(instance);
//            tx.commit();
            //      log.log(Level.WARNING, "attach successful");
//            Session session = session;
//            Object cap = session.load(Capture.class, instance.getId());

            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            session.saveOrUpdate(instance);
            tx.commit();

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void attachClean(Capture instance) {
        //   log.log(Level.WARNING, "attaching clean Capture instance");
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.lock(instance, LockMode.NONE);
            //       log.log(Level.WARNING, "attach successful");
        } catch (RuntimeException re) {
            //      log.log(Level.WARNING, "attach failed", re);
            throw re;
        }
    }

    public void delete(Capture persistentInstance) {
        //   log.log(Level.WARNING, "deleting Capture instance");
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            session.delete(persistentInstance);
            tx.commit();
            //     log.log(Level.WARNING, "delete successful");
        } catch (RuntimeException re) {
            //    log.log(Level.WARNING, "delete failed", re);
            throw re;
        }
    }

//    public void begin() {
//        session.beginTransaction();
//    }

//    public void tx.commit() {
////        begin();
//        session.getTransaction().tx.commit();
//        
//    }
//    public void rollback() {
//        try {
//            session.getTransaction().rollback();
//        } catch (HibernateException e) {
//            //    log.log(Level.WARNING, "Cannot rollback", e);
//        }
//
//        try {
//            session.close();
//        } catch (HibernateException e) {
//            //   log.log(Level.WARNING, "Cannot close", e);
//        }
//        CaptureHome.session.set(null);
//    }

    public static void close() {
        session.get().close();
        CaptureHome.session.set(null);
    }

    public Capture merge(Capture detachedInstance) {
        //  log.log(Level.WARNING, "merging Capture instance");
        try {
//            begin();
           Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
             
            Capture result = (Capture) session.merge(detachedInstance);
            tx.commit();
            //     log.log(Level.WARNING, "merge successful");
            return result;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "merge failed", re);
            throw re;
        }
    }

    public Capture findById(long id) {
        //  log.log(Level.WARNING, "getting Capture instance with id: " + id);
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            Capture instance = (Capture) session.get("com.gdit.capture.entity.Capture", id);
            if (instance == null) {
                //           log.log(Level.WARNING, "get successful, no instance found");
            } else {
                //           log.log(Level.WARNING, "get successful, instance found");
            }
            return instance;
        } catch (RuntimeException re) {
            //       log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Capture findByName(String name, int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.name = :name "
                    + "and c.category = :category  and "
                    + "c.status = :status and c.type = 1 and c.deleted = 0 ")
                    .setParameter("category", category)
                    .setParameter("status", status)
                    .setParameter("name", name).list();
            if (instances != null && instances.size() > 0) {
                return (Capture) instances.get(0);
            } else {
                return null;
            }

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<String> findByNameStr(String name, int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c.name from Capture c where c.name LIKE :name AND "
                    + "c.deleted = false AND c.status = :status AND c.locked = false"
                    + " AND c.category = :category AND c.type=1").setParameter("name", "%" + name + "%").setParameter("category", category).setParameter("status", status).list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<String> findByNames(String name, int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select UPPER(c.name) from Capture c where "
                    + "c.name LIKE :name  AND c.type = 1 AND c.category = :category AND "
                    + "c.deleted = false AND c.status = :status AND  "
                    + " order by c.id")
                    .setParameter("name", "%" + name + "%")
                    .setParameter("status", status)
                    .setParameter("category", category)
                    .list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getUserCaptures(Users user, int status, Category category) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.users = :user "
                    + "AND c.type=1 AND c.status = :status AND deleted = false "
                    + "AND c.category = :category")
                    .setParameter("user", user)
                    .setParameter("status", status)
                    .setParameter("category", category)
                    .list();
            return instances;

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Capture findByPath(String path) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.path = :path AND c.deleted is null").setParameter("path", path).list();
            if (instances != null && instances.size() > 0) {
                return (Capture) instances.get(0);
            } else {
                return null;
            }

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Capture findByNameAndParent(String name, Capture parent) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.name = :name and c.capture = :parent").setParameter("name", name).setParameter("parent", parent).list();
            if (instances != null && instances.size() > 0) {
                return (Capture) instances.get(0);
            } else {
                return null;
            }

        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getExceptionBatches() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 AND c.status = 11").list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getBatchPages(Capture batch) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.capture IN "
                    + "(select c1 from Capture c1 where c1.capture = :batch)  "
                    + "  order by c.id").setParameter("batch", batch).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Capture> getBatchUnDeletedPages(Capture batch) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.capture IN "
                    + "(select c1 from Capture c1 where c1.capture = :batch) and c.deleted = false "
                    + "  order by c.id").setParameter("batch", batch).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Capture> getBatchDeletedPages(Capture batch) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.capture IN (select c1 from Capture c1 where c1.capture = :batch and c1.deleted = true) "
                    + "  order by c.id").setParameter("batch", batch).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Capture> getDocRefusedPages(Capture doc, int status) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where "
                    + "c.capture = :doc "
                    + "and c.deleted= false  and (c.refused = true or c.name = 'page1')"
                    + "order by c.id ").setParameter("doc", doc).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Capture> getRefusedBatchPages(Capture batch) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c "
                    + "where c.capture IN (select c1 from Capture c1 where c1.capture = :batch) "
                    + "and c.refused = true"
                    + " order by c.id").setParameter("batch", batch).list();
            return instances;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isRefusedPages(Capture batch) {

        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            

            List<Capture> instances = session.createQuery("select c from Capture c where c.capture IN "
                    + "(select c1 from Capture c1 where c1.capture = :batch) and c.refused = true"
                    + " order by c.id").setParameter("batch", batch).list();
            if (instances != null && instances.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }
    }

    public List findByExample(Capture instance) {
        //  log.log(Level.WARNING, "finding Capture instance by example");
        try {
            List results = sessionFactory.getCurrentSession().createCriteria("com.gdit.capture.entity.Capture").add(Example.create(instance)).list();
            //        log.log(Level.WARNING, "find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            //        log.log(Level.WARNING, "find by example failed", re);
            throw re;
        }
    }

    public String getBatchPages(long batchId) {
        try {
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            String page = (String) session.createSQLQuery("select  p.name  from capture b,capture d,capture p "
                    + "where p.parent_id = d.id and d.parent_id = b.id "
                    + "and p.type = 3 and b.id = :batchId order by p.id Desc").setParameter("batchId", batchId).list().get(0);
            return page;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public List<Capture> getUnOcredBatches() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        List<Capture> instances = session.createQuery("select b from Capture b where "
                + "(select count(d) from Capture d where d.capture =b and d.type = 2) <=1 "
                + "AND b.type = 1 and b.deleted = false  "
                + "AND b.id not IN (select l.batchId from BatchLog l ) order by b.id").list();
        return instances;
    }

    public List<String> getUnOcredBatcheNames() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        List<String> instances = session.createQuery("select b.name from Capture b where "
                + "(select count(d) from Capture d where d.capture =b and d.type = 2) <=1 "
                + "AND b.type = 1 and b.deleted = false  "
                + "AND b.id not IN (select l.batchId from BatchLog l ) order by b.id").list();
        return instances;
    }

    public List<String> getUnOcredNames() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        List<String> instances = session.createQuery("select b.name from Capture b where "
                + "(select count(d) from Capture d where d.capture =b and d.type = 2) <=1 "
                + "AND b.type = 1 and b.deleted = false  "
                + "AND b.id not IN (select l.batchId from BatchLog l ) order by b.id").list();
        return instances;
    }

    public List getUserProductivity(Integer userId, String date) {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        return session.createSQLQuery("select user_name , to_Char(created_date,'dd/MM/YYYY') , count(1) "
                + "from Capture p , users u where p.user_id = u.id and( p.user_id = :userId or :userId = 0) "
                + "and (:date = '0' or to_Char(created_date,'dd/MM/yyyy') = :date  and p.type = 3  ) "
                + "group by user_name , to_Char(created_date,'dd/MM/YYYY') "
                + "order by to_Char(created_date,'dd/MM/YYYY') desc ")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .list();
    }

    public List getScanProductivity(Integer userId, String date) {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        return session.createSQLQuery("select t1.user_name, t1.d,b,p from "
                + "(select user_name , to_Char(created_date,'dd/MM/YYYY') d, count(1) b "
                + "from Capture p , users u where p.user_id = u.id and( p.user_id = :userId or :userId = 0) "
                + "and (:date = '0' or to_Char(created_date,'dd/MM/yyyy') = :date)   and p.type = 1  "
                + "group by user_name , to_Char(created_date,'dd/MM/YYYY') "
                + "order by to_Char(created_date,'dd/MM/YYYY') desc )t1 , "
                + "(select  user_name, to_char(created_date,'dd/MM/YYYY') d,count(*) p "
                + "from CAPTURE_VW c,users u where u.id = c.user_id  "
                + "AND (c.user_id = :userId or :userId = 0)   "
                + "AND ( :date = '0' or :date =  to_Char(created_date,'dd/MM/yyyy'))   "
                + "group by user_name, to_char(created_date,'dd/MM/YYYY') "
                + "order by  to_char(created_date,'dd/MM/YYYY') desc ,count(*) desc ) t2"
                + " where t1.user_name = t2.user_name and t1.d = t2.d ")
                .setParameter("userId", userId)
                .setParameter("date", date)
                .list();
    }

    public List getScanProductivity() {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        return session.createSQLQuery("select  user_name, to_char(created_date,'yyyy-MM-dd'),count(*) "
                + "from CAPTURE_VW c , USERS u "
                + "where u.id = c.user_id    "
                + "group by user_name, to_char(created_date,'yyyy-MM-dd') "
                + "order by  to_char(created_date,'yyyy-MM-dd') "
                + "desc ,count(*) desc")
                .list();
    }

    public List<Capture> getBatchDetail(int id, String name) {
        Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
        List<Capture> instances = session.createQuery("select c from Capture c where "
                + "(:id = 0 or c.id = :id) "
                + "AND (:name = '0' or c.name = :name ) "
                + "").setParameter("id", id).setParameter("name", name).list();
        return instances;

    }

    public List<Capture> getWatingIndexing() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where "
                    + "c.type = :type AND "
                    + "c.status = :status AND "
                    + "c.locked = :locked").setParameter("type", 1).setParameter("status", CaptureStatus.IndexMode).setParameter("locked", false).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }
    public List<Capture> getWatingIndexing(Category cat) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where "
                    + "c.type = :type AND "
                    + "c.status = :status AND "
                    + "c.category = :category AND "
                    + "c.locked = :locked")
                    .setParameter("type", 1)
                    .setParameter("status", CaptureStatus.IndexMode)
                    .setParameter("locked", false)
                    .setParameter("category", cat)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getIndexedException() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where "
                    + "c.type = :type AND "
                    + "c.status = :status AND "
                    + "c.locked = :locked").setParameter("type", 1).setParameter("status", CaptureStatus.INDEX_EXCEPTION).setParameter("locked", false).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getIndexedBatches() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c "
                    + " where c.type = :type "
                    + " AND c.status = :status").setParameter("type", 1).setParameter("status", CaptureStatus.INDEXED).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getAllBatches(int status, long id) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + "c.status = :status and c.id > :id order by c.id").setParameter("status", status).setParameter("id", id).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getAllBatches(int status, long id, long to) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + "c.status = :status and  c.id between :id and :to order by c.id").setParameter("status", status).setParameter("id", id).setParameter("to", to).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getAllBatches(long id, long to) {
        try {
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> instances = session.createQuery("select c from Capture c where c.type = 1 and c.deleted = 0 and "
                    + " c.id between :id and :to order by c.id")
                    .setParameter("id", id)
                    .setParameter("to", to).list();
            return instances;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Capture> getAllBatches(long id) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 1 "
                    + "and c.deleted = 0   and c.id >= :id order by c.id   ").setParameter("id", id).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getAllPages(long id) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.id > :id order by c.id   ").setParameter("id", id).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getAllPages(long from, long to) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.id between :from and :to order by c.id   ").setParameter("from", from).setParameter("to", to).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getErrorPages(long from, long to) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.id between :from and :to and c.refuseNote IN ( 'ERROR','NOTFOUND') "
                    + " order by c.id   ").setParameter("to", to).setParameter("from", from).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getErrorPages(long from, int status) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.id >= :from   and c.refuseNote IN ( 'ERROR','NOTFOUND') "
                    + " order by c.id   ").setParameter("from", from).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Set<Capture> getErrorBarches() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select   c.capture.capture from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.refuseNote IN ( 'ERROR','NOTFOUND') "
                    + " order by c.id   ").list();
            Set set = new HashSet();
            set.addAll(instances);
            return set;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Set<Capture> getErrorBarches(long id) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select   c.capture.capture from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.refuseNote IN ( 'ERROR','NOTFOUND') and c.id > :id "
                    + " order by c.id   ").setParameter("id", id).list();
            Set set = new HashSet();
            set.addAll(instances);
            return set;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getDiskImages(long status, Disk disk) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where c.type = 3 "
                    + "and c.deleted = 0 and c.status = :status  "
                    + " order by c.id   ").setParameter("status", status).setParameter("disk", disk).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Capture GetSon(Capture parent) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  c.capture =:capture ").setParameter("capture", parent).list();
            return (Capture) instances.get(0);
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> GetSons(Capture parent) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  c.capture =:capture ").setParameter("capture", parent).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List findDuplicateBarcode() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createSQLQuery("select barcode,count(*) from Capture "
                    + "where type = 2 and deleted = 0 "
                    + "group by barcode HAVING(count(barcode) > 1) "
                    + "order by barcode ").list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> findByBrcode(String barcode) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.barcode =:barcode order by c.barcode").setParameter("barcode", barcode).list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> findByBrcode(String barcode, long id) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.barcode =:barcode and c.id <> :id order by c.barcode")
                    .setParameter("barcode", barcode)
                    .setParameter("id", id)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getUnbarcodedDocs(long id) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.id > :id and c.type = 2 and c.deleted=0 and c.barcode is null or c.barcode  ='null' "
                    + " order by c.capture.id")
                    .setParameter("id", id)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getDocs(long from, long to) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.type = 2 and c.deleted =0  and c.capture.id between :from and :to "
                    + " order by c.capture.id")
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> deletedBatches() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.type = 1 and c.deleted =1  order by c.id")
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getWrongBarcodeBatches(long from, long to) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.type = 1 and c.barcode = 'WRONG' and c.id between :from and :to"
                    + "  order by c.id")
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getAllDocs() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select c from Capture c where  "
                    + "c.type = 2 and c.barcode <> 'null' and c.barcode is not null and c.barcoded = false"
                    + "  order by c.id ")
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<String> getAllBarcodes() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select distinct c.barcode from Capture c where  "
                    + "c.type = 2 and c.barcode <> 'null' and c.barcode is not null order by barcode")
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<String> getAllBarcodes(String from, String to) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List instances = session.createQuery("select distinct c.barcode from Capture c where  "
                    + "c.type = 2 and c.barcode <> 'null' and c.barcode is not null "
                    + "and c.id not in (select d.docId from EDocuments d ) "
                    + "and c.barcode <= :from and c.barcode > :to "
                    + "order by barcode")
                    .setParameter("from", from)
                    .setParameter("to", to)
                    .list();
            return instances;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public long getCountBatches(Computers computer) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            long count = (Long) session.createQuery("select count(*) from Capture c where "
                    + "c.computer = :computer and type = 1")
                    .setParameter("computer", computer)
                    .list().get(0);
            return count;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getComputerUnBarcodedDocs(Computers computer) {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> count = (List<Capture>) session.createQuery("select c from Capture c where "
                    + "c.computer = :computer and type = 2 and c.barcode is null and c.deleted = 0")
                    .setParameter("computer", computer)
                    .list();
            return count;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public List<Capture> getComputerUnBarcodedDocs() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> count = (List<Capture>) session.createQuery("select c from Capture c where "
                    + " type = 2 and c.barcode is null and c.deleted = false order by c.id ")
                    .list();
            return count;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Date getSysDate() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            Date date = (Date) session.createSQLQuery("select SYSDATE from dual ")
                    .list().get(0);
            return date;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }

    public Capture getNextBatch() {
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            Capture batch = (Capture) session.createQuery("select c from Capture c where c.type =1 and "
                    + "c.deleted = 0 order by c.id ")
                    .list().get(0);
            return batch;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }
    
    public List<Capture> getMissedBatches(){
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> batches = (List<Capture>)  session.createQuery("select c from Capture c "
                    + "where c.users.id =1 and c.type =1 and "
                    + "c.deleted = 0 order by c.id ")
                                    .list() ;
            return batches;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }
    
    public List<Capture> getReSeperated(int rep){
        try {
            // TODO solve status to 6;
//            begin();
            Session session = ApplicationSession.getSession();
            Transaction tx = session.beginTransaction();
            
            List<Capture> batches = (List<Capture>)  session.createSQLQuery("select * from Capture c "
                    + "where    c.type =1 and c.deleted = 0 "
                    + "and c.rep_id = :rep and "
                    + "(select count(*) from Capture where type = 2 and  parent_id = c.id ) <10 "
                    + "order by c.id ")
                    .setParameter("rep", rep)
                                    .list() ;
            return batches;
        } catch (RuntimeException re) {
            //     log.log(Level.WARNING, "get failed", re);
            throw re;
        }
    }
//    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}

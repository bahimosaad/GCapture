package com.gdit.capture.entity;
// Generated 27/10/2010 12:03:54   by Hibernate Tools 3.2.0.beta7


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
public class ComputersHome {

   

/** 
* Dependency injecting constructor. 
*/ 
public ComputersHome() { 
 
} 
    
 public static Session getSession() {
      Session session = (Session) ComputersHome.session.get();
      if (session == null) {
         session = sessionFactory.openSession();
         ComputersHome.session.set(session);
      }
      return session;
   }
    public void persist(Computers transientInstance) {
        log.log(Level.WARNING,"persisting Computers instance");
        try {
	     begin();
            getSession().persist(transientInstance);
            log.log(Level.WARNING,"persist successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"persist failed", re);
            throw re;
        }
    }
    
    public List<Computers> getAllComputers(){
	    Query q = getSession().createQuery("from Computers");
	    List<Computers> list = (List<Computers>) q.list();
	    return list;
	}
    public void attachDirty(Computers instance) {
        log.log(Level.WARNING,"attaching dirty Computers instance");
        try {
	     begin();
            getSession().saveOrUpdate(instance);
            log.log(Level.WARNING,"attach successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Computers instance) {
        log.log(Level.WARNING,"attaching clean Computers instance");
        try {
             begin();
            getSession().lock(instance, LockMode.NONE);
            log.log(Level.WARNING,"attach successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"attach failed", re);
            throw re;
        }
    }
    
    public void delete(Computers persistentInstance) {
        log.log(Level.WARNING,"deleting Computers instance");
        try {
	     begin();
            getSession().delete(persistentInstance);
            log.log(Level.WARNING,"delete successful");
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"delete failed", re);
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
      } catch( HibernateException e ) {
         log.log(Level.WARNING,"Cannot rollback",e);
      }
      
      try {
         getSession().close();
      } catch( HibernateException e ) {
         log.log(Level.WARNING,"Cannot close",e);         
      }
      ComputersHome.session.set(null);
   }
   
   public static void close() {
      getSession().close();
      ComputersHome.session.set(null);
   }
   
   private static final Logger log = Logger.getAnonymousLogger();

   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   private static final SessionFactory sessionFactory = new Configuration()
         .configure().buildSessionFactory();

    
    public Computers merge(Computers detachedInstance) {
        log.log(Level.WARNING,"merging Computers instance");
        try {
            Computers result = (Computers) getSession().merge(detachedInstance);
            log.log(Level.WARNING,"merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"merge failed", re);
            throw re;
        }
    }
    
    public Computers findById( int id) {
        log.log(Level.WARNING,"getting Computers instance with id: " + id);
        try {
	     begin();
            Computers instance = (Computers) getSession()
                    .get("com.gdit.capture.entity.Computers", id);
            if (instance==null) {
                log.log(Level.WARNING,"get successful, no instance found");
            }
            else {
                log.log(Level.WARNING,"get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"get failed", re);
            throw re;
        }
    }

    public Computers findByName(String name){
        try{
            Query q = getSession().createQuery("select c from Computers c where upper(c.name) = :name")
                    .setParameter("name", name);
            List<Computers> computers  = q.list();
            if(computers!=null && computers.size()>0)
                return computers.get(0);
            else
                return null;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    public Computers findByIP(String ip){
        try{
            Query q = getSession().createQuery("select c from Computers c where c.ip = :ip").setParameter("ip", ip);
            List<Computers> computers  = q.list();
            if(computers!=null && computers.size()>0)
                return computers.get(0);
            else
                return null;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public List findByExample(Computers instance) {
        log.log(Level.WARNING,"finding Computers instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("com.gdit.capture.entity.Computers")
                    .add(Example.create(instance))
            .list();
            log.log(Level.WARNING,"find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.log(Level.WARNING,"find by example failed", re);
            throw re;
        }
    } 
}


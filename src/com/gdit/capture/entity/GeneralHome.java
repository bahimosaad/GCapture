/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

import java.util.List;
import org.hibernate.LockMode;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class Roles.
 * @see com.gdit.capture.entity.Roles
 * @author Hibernate Tools
 */
import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;

/**
 *
 * @author hp
 */
public class GeneralHome {

    public static Session getSession() {
        Session session = (Session) GeneralHome.session.get();
        if (session == null) {
            session = sessionFactory.openSession();
            GeneralHome.session.set(session);
        }
        return session;
    }

    public void begin() {
        getSession().beginTransaction();
    }

    public List<Roles> getUsersRoles(Users user) {
        try {
            Query q = getSession().createQuery("Select r from Roles r ,Users u, UsersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND ug.users = u AND u = :user ").setParameter("user", user);
            List<Roles> roles = q.list();
            return roles;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Modules> getUsersModules(Users user) {
        try {
            Query q = getSession().createQuery("Select m from Modules m, ModulesRoles mr, Roles r ,Users u, UsersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND ug.users = u AND mr.roles = r AND mr.modules = m  AND u = :user ").setParameter("user", user);
            List<Modules> modules = q.list();
            return modules;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean authnticateUserModule(Users user, String moduleName) {
        try {
            Query q = getSession().createQuery("Select m from Modules m, ModulesRoles mr, Roles r ,Users u, "
                    + "UsersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND ug.users = u AND mr.roles = r AND mr.modules = m  AND u = :user  AND m.name = :moduleName").setParameter("user", user).setParameter("moduleName", moduleName);
            List<Modules> modules = q.list();
            if (modules != null && modules.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean authnticateUserCategory(Users user, Category category) {
        try {
            Query q = getSession().createQuery("Select c from Category c,RolesCategories rc , Users u,Roles r, "
                    + "UsersGroups ug,RolesGroups rg where ug.groups = rg.groups AND rc.category = :category "
                    + "AND rc.roles = r "
                    + "AND rg.roles = r AND ug.users = u AND rc.category = :category AND u = :user AND c = :category").setParameter("user", user).setParameter("category", category);
            List<Category> reps = q.list();
            if (reps != null && reps.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public List<Category> authnticateUserCategory(Users user, Rep rep) {
        try {
            Query q = getSession().createQuery("Select c from Category c,RolesCategories rc , Users u,Roles r, "
                    + "UsersGroups ug,RolesGroups rg where ug.groups = rg.groups AND rc.category = c "
                    + "AND rc.roles = r "
                    + "AND rg.roles = r AND ug.users = u AND rc.category = c AND u = :user "
                    + "AND c.rep = :rep order by c.id").setParameter("user", user).setParameter("rep", rep);
            return q.list();
//            if (reps != null && reps.size() > 0) {
//                return true;
//            } else {
//                return false;
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Roles> getComputersRoles(Computers computer) {
        try {
            Query q = getSession().createQuery("Select r from Roles r ,Computers u, ComputersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND ug.computers = u AND u = :computer ").setParameter("computer", computer);
            List<Roles> roles = q.list();
            return roles;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Rep> getRoleReps(Roles role) {
        Query q = getSession().createQuery("select rr.rep from RolesReps rr where rr.roles = :role").setParameter("role", role);
        List<Rep> reps = q.list();
        return reps;
    }

    public List<Modules> getComputersModules(Computers computer) {
        try {
            Query q = getSession().createQuery("Select m from Modules m , MdulesRoles mr, Roles r ,Computers u, ComputersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND mr.modules = m AND mr.roles = r AND ug.computers = u AND u = :user ").setParameter("user", computer);
            List<Modules> modules = q.list();
            return modules;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean authunticateComputerModule(Computers computer, String moduleName) {
        try {
            Query q = getSession().createQuery("Select m from Modules m , ModulesRoles mr, Roles r ,Computers u, ComputersGroups ug , RolesGroups rg where ug.groups = rg.groups "
                    + " AND rg.roles = r AND mr.modules = m AND mr.roles = r AND ug.computers = u AND u = :user AND m.name = :moduleName").setParameter("user", computer).setParameter("moduleName", moduleName);
            List<Modules> modules = q.list();
            if (modules != null && modules.size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
        GeneralHome.session.set(null);
    }

    public static void close() {
        getSession().close();
        GeneralHome.session.set(null);
    }

    public void upload(Long id) {
        try {
            begin();
            getSession().createSQLQuery("INSERT INTO   VASCULAR.DOCUMENT_DATA (FIELD_ID,DOC_ID,FIELD_VAL)"
                    + " select  25,c.id,doc_no  from CAPTURE.CAPTURE c ,CAPTURE.PATIENTS_DOC p WHERE"
                    + " c.barcode = p.id and c.status = 4   ").setParameter("id", id).executeUpdate();
            commit();
        } catch (Exception ex) {
            System.out.println(id);
            //ex.printStackTrace();
        }
    }

    public void uploadInv(Long id) {
        try {
            begin();
            getSession().createSQLQuery("INSERT INTO   VASCULAR.DOCUMENT_DATA (FIELD_ID,DOC_ID,FIELD_VAL)"
                    + " select  28, TO_CHAR(c.id),i.name from CAPTURE.CAPTURE c ,CAPTURE.PATIENTS_DOC p,Investigation i WHERE"
                    + " c.barcode = p.id and c.status = 4 and i.id = p.investigation AND  ").setParameter("id", id).executeUpdate();
            commit();
        } catch (Exception ex) {
            System.out.println(id);
            //ex.printStackTrace();
        }
    }

    public void uploadDate(Long id) {
        try {
            begin();
            getSession().createSQLQuery("INSERT INTO   VASCULAR.DOCUMENT_DATA (FIELD_ID,DOC_ID,FIELD_VAL)"
                    + " select  26,  TO_CHAR(c.id),Investigation_date from CAPTURE.CAPTURE c ,CAPTURE.PATIENTS_DOC p WHERE"
                    + " c.barcode = p.id and c.status = 4 ").setParameter("id", id).executeUpdate();
            commit();
        } catch (Exception ex) {
            System.out.println(id);
            //ex.printStackTrace();
        }
    }

    public void uploadImage(Long id) {
        try {
            begin();
            getSession().createSQLQuery("INSERT INTO   VASCULAR.DOCUMENT_DATA (FIELD_ID,DOC_ID,FIELD_VAL) "
                    + "select  41, TO_CHAR(c.id),Investigation_date from CAPTURE.CAPTURE c ,CAPTURE.PATIENTS_DOC p  WHERE "
                    + "c.barcode = p.id and c.status = 4 and img =1").setParameter("id", id).executeUpdate();
            commit();
        } catch (Exception ex) {
            System.out.println(id);
            //ex.printStackTrace();
        }
    }
    private static final Logger log = Logger.getAnonymousLogger();
    private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
}

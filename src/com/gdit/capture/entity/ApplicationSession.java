/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ehab
 */
public class ApplicationSession {

    private static Session SESSION;
    public static final ThreadLocal LOCAL = new ThreadLocal();
    
    public static Session getSession() {
        if (SESSION == null || !SESSION.isOpen()) {
           SESSION = (Session) LOCAL.get();
           if (SESSION == null){
            SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
            SESSION = sessionFactory.openSession();
            SESSION.setFlushMode(FlushMode.AUTO);
            LOCAL.set(SESSION);
           }
        }
        SESSION.setFlushMode(FlushMode.COMMIT);
        return SESSION;
    }

    public static void endSession() {
        try {
            if (SESSION != null) {
                SESSION.flush();
                SESSION.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

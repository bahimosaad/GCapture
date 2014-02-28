/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdit.capture.entity;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ehab
 */
public class DocumentDataHome02 {

    public long persist(DocumentData transientInstance) {
        long id = 0;
        Session session = ApplicationSession.getSession();
        Transaction transaction = session.beginTransaction();
        id = (Long) session.save(transientInstance);
        transaction.commit();
        return id;
    }

    public void update(DocumentData transientInstance) {
        Session session = ApplicationSession.getSession();
        Transaction transaction = session.beginTransaction();
        session.saveOrUpdate(transientInstance);
        transaction.commit();
    }

    public List<DocumentData> getAllDocuments() {
        List<DocumentData> documents = null;
        Session session = ApplicationSession.getSession();
        documents = session.createQuery("select d from DocumentData d").list();
        return documents;
    }

    public List<DocumentData> getDocumentDataByID(Long id) {
        List<DocumentData> documents = null;
        Session session = ApplicationSession.getSession();
        documents = session.createQuery("select d from DocumentData d where d.docId =" + id).list();
        return documents;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;


import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import org.hibernate.Transaction;
import server.Department;

/**
 *
 * @author TJ
 */
public class PersistenceManager {

    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static EntityTransaction  t;

    public static List executeQuery(String q) {
        if(em==null) {
            init();
        }
        if(!t.isActive()) {
            t.begin();
        }
        Query q2 = em.createQuery(q);
        return q2.getResultList();

    }
    private static void init() {
       emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
        t=em.getTransaction();
    }
    public static void persist(Object o) {
        if(em==null) {
            init();
        }
       if(!t.isActive()) {
            t.begin();
        }
       em.persist(o);
       t.commit();

    }
    public static Object find(Object o, Object pk) {
        return find(o.getClass(), pk);
    }
    public static void remove(Object o, Object pk) {
         if(em==null) {
            init();

        }
        if(!t.isActive()) {
            t.begin();
        }
       Object e=find(o, pk);
       em.remove(e);
       t.commit();
    }
    public static void merge(Object o) {
        if(em==null) {
            init();

        }
        if(!t.isActive()) {
            t.begin();
        }
       em.merge(o);
       t.commit();
    }
    //public static boolean exists()
}

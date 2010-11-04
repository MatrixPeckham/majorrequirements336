/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.*;
import javax.persistence.*;
import server.User;
import server.User;
/**
 *
 * @author TJ
 */
public class SystemManager {
       private static SystemManager s;
       private long nextAvailible;
       private TreeMap<Long, User> users;
       private static EntityManagerFactory emf;
       private static EntityManager em;
       private SystemManager() {
           emf=Persistence.createEntityManagerFactory("ClientPU");
           em=emf.createEntityManager();

            nextAvailible=0;
            users=new TreeMap<Long,User>();
       }

       public static SystemManager getSystemManager() {
           if(s==null) {
               s=new SystemManager();
           }
           return s;
       }
       public int checkLogin(String user, String pass) {
           Query q=em.createNativeQuery("Select permissions from USERS where USERNAME= ?user AND PASSWORD= ?pass");
           q.setParameter("user", user);
           q.setParameter("pass", pass);
           Integer i=(Integer)q.getSingleResult();
           if(i!=null) {
               return i;
           } else {
               return User.STUDENT;
           }
       }
       public void removeUser(long id) {
            users.remove(id);
       }
       public User addUser() {
           User u=new User(nextAvailible);
           users.put(nextAvailible, u);
           nextAvailible++;
           return u;
       }
}

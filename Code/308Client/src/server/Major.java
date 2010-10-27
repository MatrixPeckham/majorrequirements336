/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;

/**
 *
 * @author tj
 */
@Entity
public class Major implements Serializable {
    
    private String id;
    private Collection<Requirement> reqs;
    private static EntityManagerFactory emf;
    private static EntityManager em;
     public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
        Major c=new Major();
        em.getTransaction().begin();
        em.persist(c);

        c.setId("CSE");

        em.getTransaction().commit();
    }

     @OneToMany()
     public Collection<Requirement> getRequirements() {
         return reqs;
     }
     public void setRequirements(Collection<Requirement> r) {
         reqs=r;
     }
     public Major() {
         reqs=new ArrayList<Requirement>();
     }
@Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
     
  
}

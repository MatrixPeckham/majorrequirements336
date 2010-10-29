/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.logging.Level;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import logging.UseLogger;

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
    private static UseLogger logger;

    public Major() {
        logger = new UseLogger();
        reqs=new ArrayList<Requirement>();
    }
     public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
        Major c=new Major();
        c.setId("ISE");
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        // Set the LogLevel to Info, severe, warning and info will be written
	// Finest is still not written
	UseLogger.LOGGER.setLevel(Level.INFO);
	UseLogger.LOGGER.severe("Info Log");
	UseLogger.LOGGER.warning("Info Log");
	UseLogger.LOGGER.info("Info Log");
    }

     @OneToMany()
     public Collection<Requirement> getRequirements() {
         return reqs;
     }
     public void setRequirements(Collection<Requirement> r) {
         reqs=r;
     }

@Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

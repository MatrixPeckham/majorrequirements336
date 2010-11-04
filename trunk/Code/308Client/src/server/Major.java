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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import logging.UseLogger;

/** Contains info and actions for majors
 *
 * @author tj
 */
@Entity
public class Major implements Serializable {
    
    private String id;
    
    private Collection<Requirement> reqs;
    private double minGPA;
    private int minCreditsHere;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static UseLogger logger;

    public Major() {
        logger = new UseLogger();
        reqs=new ArrayList<Requirement>();
    }
    /**
     * test main method
     * @param args
     */
     public static void main(String[] args) {
        emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
        em.getTransaction().begin();
        Major c=new Major();
        c.setId("CSE2");
        Course co=new Course();
        co.setId("CSE39");
        Departments d=new Departments();
        d.setName("CSE2");
        em.persist(d);
        d.addMajor(c);
        d.addCourse(co);
        em.getTransaction().commit();

        // Set the LogLevel to Info, severe, warning and info will be written
	// Finest is still not written
	
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
    /**
     * Adds requirement to major and saves major
     * @param r
     */
    public void addRequirement(Requirement r) {
        try {
        em.merge(r);
        reqs.add(r);
        em.merge(this);
        }catch(Exception e) {

        }
    }
    public Vector<Course> getRemainingCourse(Vector<CourseRecord> records) {
        return null;
    }
    public void removeRequirement(Requirement r) {

    }

}

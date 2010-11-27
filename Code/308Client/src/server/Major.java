/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import logging.UseLogger;
import structures.RootlessTree;

/** Contains info and actions for majors
 *
 * @author tj
 */
@Entity
public class Major implements Serializable {
    
    @Id
    private String id;
    @OneToMany(fetch=FetchType.EAGER)
    private Collection<Requirement> reqs;
    private String department;
    private double minGPA;
    private int minCreditsHere;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static UseLogger logger;

    public Major() {
        logger = new UseLogger();
        reqs=new ArrayList<Requirement>();
    }
    public Major(String major, double minGPA, int minLocalCreds) {
        reqs=new ArrayList<Requirement>();
        id=major;
        this.minGPA=minGPA;
        minCreditsHere=minLocalCreds;
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
        Department d=new Department();
        d.setName("CSE2");
        em.persist(d);
        d.addMajor(c);
        d.addCourse(co);
       // em.getTransaction().commit();

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
        //em.merge(r);
        reqs.add(r);
        //em.merge(this);
        }catch(Exception e) {

        }
    }
    public RootlessTree<Course> getRemainingCourse(TreeMap<String, CourseRecord> records, int year) {
        RootlessTree<Course> remaining=new RootlessTree<Course>();
        for(Requirement r : reqs) {
            if(year==r.getYear()) {
                RootlessTree<Course> c=r.getRemainingCourses(records);
                remaining.addTree(c);
            }
        }
        return remaining;
    }
    public MajorCompletion requirementsRemaining(TreeMap<String, CourseRecord> r, int year) {
        MajorCompletion mc=new MajorCompletion();
        mc.name=this.getId()+" Major Requirements";
        for(Requirement re : reqs) {
            if(re.getYear()==year) {
               RequirementCompletion rc=re.requirementSatisfied(r);
                mc.reqs.add(rc);
            }
        }
        return mc;
    }
    public void removeRequirement(Requirement r) {

    }
    public String getDepartment() {return department;}
    public void setDepartment(String dept) {department=dept;}
    public String toString() {return getId();}
}

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
import javax.persistence.CascadeType;
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
import persistence.PersistenceManager;
import persistence.Updater;
import structures.RootlessTree;

/** Contains info and actions for majors
 *
 * @author tj
 */
@Entity
public class Major implements Serializable, Updater {
    
    @Id
    private String id;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private Collection<Requirement> reqs;
    private String department;
    private static EntityManagerFactory emf;
    private static EntityManager em;
    private static UseLogger logger;

    public Major() {
        logger = new UseLogger();
        reqs=new ArrayList<Requirement>();
    }
    public Major(String major) {
        reqs=new ArrayList<Requirement>();
        id=major;
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
            reqs.add(r);
            PersistenceManager.merge(this, this.getId());
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
        r.getPossibleCourses().clear();
        PersistenceManager.persist(r);
        reqs.remove(r);
        PersistenceManager.merge(this, this.getId());
    }
    public String getDepartment() {return department;}
    public void setDepartment(String dept) {department=dept;}
    public String toString() {return getId();}
    public boolean equals(Object o) {
        if(o instanceof Major){
            return this.id.equals(((Major)o).getId());
        }
        else
            return false;
    }

    @Override
    public void update(Object toUpdate) {
        Major set=(Major) toUpdate;
        set.setRequirements(reqs);
        set.setId(id);
   }
}
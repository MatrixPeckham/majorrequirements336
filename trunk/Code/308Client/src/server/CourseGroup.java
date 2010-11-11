/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import structures.*;

/**Course Group
 * TRhis class represents a sequence of courses
 * all courses in a course group must be completed
 * @author tj
 */

@Entity
public class CourseGroup implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//a unique number for the course sequence
    private double minGPA;//minGPA to complete the sequence successfully
    @OneToMany
    private Collection<Course> courses;//Required courses- maps to courses in course table
     private static EntityManager em;
    private static EntityManagerFactory emf;

    public CourseGroup() {
        courses=new ArrayList();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public double getMinGPA() {
        return minGPA;
    }
    public void setMinGPA(double d) {
        minGPA=d;
    }
    public Collection<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> a) {
        courses=a;
    }
    /**
     * Adds Course and persists sequence in database
     * @param c
     */
    public void addCourse(Course c) {
        //em.merge(c);
        courses.add(c);
        //em.merge(this);
        //em.getTransaction().commit();
    }
    /**
     * Removes Course and persists sequence in database
     * @param c
     */
    public void removeCourse(Course c) {
        em.getTransaction().begin();
        courses.remove(c);
        em.merge(this);
        em.getTransaction().commit();
    }
    public RootlessTree<Course> getRemainingCourses(Collection<CourseRecord> records) {
        Vector<Course> remaining=new Vector<Course>();
        RootlessTree<Course> rem=new RootlessTree<Course>();
        //go through required courses
        for(Course c : courses) {
            if(!courseComplete(c, records)) {
                getRemainingCourses(null,c,records, rem);
            }

            //remaining.addAll(getRemainingCourses(c,records));

        }
        return rem;
    }
    /**
     * 
     * @param c
     * @param records
     * @return
     */
    private void getRemainingCourses(Course parent, Course c, Collection<CourseRecord> records, RootlessTree<Course> remaining){
       
        if(!courseComplete(c, records)) {
            if(parent==null) {
                remaining.addRoot(c);
            } else {
                remaining.addChild(parent, c);
            }
                Collection<Course> prereqs=c.getPrereqs();
                for(Course c2 : prereqs) {

                    getRemainingCourses(c, c2, records, remaining);
                }
        }
        
    }
    private boolean courseComplete(Course c, Collection<CourseRecord> records) {
        for(CourseRecord r :records) {
            if(c.equals(r.getCourse()) && r.coursePassed()) {
                return true;
            }
        }
        return false;
    }

}

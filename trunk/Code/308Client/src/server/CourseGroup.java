/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

    CourseGroup() {
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
        em.merge(c);
        courses.add(c);
        em.merge(this);
        em.getTransaction().commit();
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
    /**
     *
     * @return whether the sequence has been completed
     */
    public boolean sequenceSatisfied(){//Collection<CourseRecord> c) {
        return false;
    }

    

}

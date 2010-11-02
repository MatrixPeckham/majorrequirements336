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
import javax.persistence.Persistence;
import logging.UseLogger;

/** Requirements that need to be fullfiledd for a major for graduation
 *
 * @author tj
 */
@Entity
public class Requirement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;//(Primary key) name identifier for requirement
    private int numberOfCourses;/* min number of course groups to complete to satisfy requirement*/
    private double minGPA;//min Gpa for passing requirement
    private int minUpperDivCredits;// minimum upper divesion credits required
    private int minResidentCredits;//minumum non-transfer credits required
    private int version;//year of requirement
    private static EntityManager em;
    private static EntityManagerFactory emf;
    Requirement() {
        emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
    }
    @OneToMany
    private Collection<CourseGroup> possibleCourses;
    
    public Collection<CourseGroup> getPossibleCourses() {
        return possibleCourses;
    }
    public void setPossibleCourses(ArrayList<CourseGroup> c) {
        possibleCourses=c;
    }
    public int getNumberOfCourses() {
        return numberOfCourses;
    }
    public double getMinGPA() {
        return minGPA;
    }
    public int getMinUpperDivCredits() {
        return this.minUpperDivCredits;
    }
    public int getMinResidentCredits() {
        return minResidentCredits;
    }
    public void setNumberOfCourses(int c) {
        numberOfCourses=c;
    }
    public void setMinGPA(int c) {
        minGPA=c;
    }
    public void setMinUpperDivCredits(int c) {
        this.minUpperDivCredits=c;
    }
    public void setMinResidentCredits(int c) {
        minResidentCredits=c;
    }
    public int getYear() {return version;}
    public void setYear(int year) {this.version=year;}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /**
     * Adds set of required courses to requirement
     * @param c
     */
    public void addCourseGroup(CourseGroup c) {
        try {
            em.merge(c);
            possibleCourses.add(c);
            em.merge(this);
            UseLogger.info("Course Goup added to Requirement "+id);
        } catch(Exception e) {
            UseLogger.severe("line 80 Exception! :" +e.getMessage());
        }
    }

}

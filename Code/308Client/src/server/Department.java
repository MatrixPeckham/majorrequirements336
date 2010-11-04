/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Persistence;
import logging.UseLogger;

/**
 * Department
 * Manages Courses in a given department. This class
 * also determines what majors a given administrator can edit based on his department
 *
 * @author TJ
 */
@Entity
public class Department implements Serializable {
    @Id
     private String name;//(Primary Key) String identifying the department
    @OneToMany(cascade=CascadeType.PERSIST)
    private Collection<Course> courses;//courses belonging to department(not necessarily major)
    @OneToMany
    private Collection<Major> majors;//Majors that department manages-A department admin can edit theese majors
    private static EntityManager em;
    private static EntityManagerFactory emf;


    public Department() {
        emf=Persistence.createEntityManagerFactory("ClientPU");
        em=emf.createEntityManager();
       courses=new ArrayList();
        majors=new ArrayList();
    }
    
    public String getName() {
        return name;
    }
    public void setName(String s) {
        this.name=s;
    }
    public Collection<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> c) {courses=c;}
    public Collection<Major> getMajors() {
        return majors;
    }
    public void setMajors(ArrayList<Major> c) {majors=c;}
    /**
     * Adds course to department and saves department info
     * @param c
     */
    public void addCourse(Course c) {
        try {
        em.getTransaction().begin();
            //em.persist(c);
            courses.add(c);

           em.getTransaction().commit();
        UseLogger.info("Course "+c.getId()+" added to department.");
        }catch(Exception e) {;
            UseLogger.severe("Error adding course :"+c.getId()+" at line 75"+ e.getMessage());
        }
    }
    public void removeCourse(Course c) {
        try{
           // em.getTransaction().begin();
       courses.remove(c);
        //em.persist(this);

        UseLogger.info("Course "+c.getId()+" removed");
        }catch(Exception e) {
            UseLogger.severe("Error removing course :"+c.getId()+" at line 71"+ e.getMessage());
        }
    }
    /*
     * adds major to department
     */
    public void addMajor(Major m) {
        try {
            em.getTransaction().begin();
            em.persist(m);
            majors.add(m);
            
           em.getTransaction().commit();
            UseLogger.info("Major "+m.getId()+" added to database.");
        }catch(Exception e) {
            UseLogger.severe("Error adding major :"+m.getId()+" at line 75"+ e.getMessage());
        }
    }
    public void removeMajor(Major m) {
        try{
        majors.remove(m);
        //em.merge(this);
        UseLogger.info("Major "+m.getId()+" removed.");
        }catch(Exception e) {
            UseLogger.severe("Error adding major :"+m.getId()+" at line 90"+ e.getMessage());
        }
    }

}

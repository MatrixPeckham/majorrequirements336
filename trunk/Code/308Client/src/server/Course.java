/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import logging.UseLogger;
//import javax.persistence.criteria.Fetch;
//import org.eclipse.persistence.internal.oxm.schema.model.All;

/**
 *
 * @author tj
 */
@Entity
public class Course implements Serializable {
    @OneToMany
    private Collection<Course> prereqs;
    /*
     * prereqs- List of prequequisite courses maps from Courses table to itself
     */
    @Id
    private String id;
    /*
     * id- the combined name/number to uniquely identify the course (primary key)
     */
    private String minGrade;//min passing grade for the course
    private String description;//course description
    private String name;//course name, department identifier
    private int num; //course number, used for upper division identification
    private int credits;//credits satisfied by the course
    private int semestersOffered;//integer representing combination of semesesters when the course is offered
    private static EntityManager em;//JPA interaction
    private static EntityManagerFactory emf;


    public Course() {}
    Course(String dept, int level) {
        UseLogger logger = new UseLogger();
        id=dept+""+level;
        name=dept;

        num=level;
    }
   
  
    public void addPrereq(Course c) {
        em.merge(c);
    }
   
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name=name;
        em.merge(this);
    }
    public String getName() {return name;}
    public void setNum(int num) {this.num=num; em.merge(this);}
    public int getNum(){return num;}
    
    public Collection<Course> getPrereqs() {
        return prereqs;
    }
    public void setPrereqs(Collection<Course> p) {
        prereqs=p;
    }
    /* adds prereq and saves course in DB*/
    public void addPreReq(Course c) {
        em.merge(c);
        prereqs.add(c);
        em.merge(this);
    }
    public boolean passedCourse(User u){return false;}
}

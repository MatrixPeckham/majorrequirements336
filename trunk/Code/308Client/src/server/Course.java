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
    public static final int SPRING=2;
    public static final int FALL=1;
    public static final int NONE=0;
    public static final int BOTH=3;
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
   public void addPreReq(Course c) {prereqs.add(c);}
   
    public void setPrereqs(Collection<Course> p) {prereqs=p;}
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name=name;}
    public void setNum(int num) {this.num=num; em.merge(this);}

    public String getName() {return name;}
    public int getNum(){return num;}
    public String getId() {return id;}
    public Collection<Course> getPrereqs() {return prereqs;}


    public boolean passedCourse(User u){return false;}
}

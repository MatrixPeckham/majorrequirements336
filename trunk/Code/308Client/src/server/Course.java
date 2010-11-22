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
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import logging.UseLogger;
//import javax.persistence.criteria.Fetch;
//import org.eclipse.persistence.internal.oxm.schema.model.All;
import structures.*;

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
    @OneToMany(cascade=CascadeType.ALL)
    private Collection<CourseGroup> prereqs;
    private int minLevel;
    /*
     * prereqs- List of prequequisite courses maps from Courses table to itself
     */
    @Id
    private String id;
    /*
     * id- the combined name/number to uniquely identify the course (primary key)
     */
    @OneToOne
    private Grade minGrade;//min passing grade for the course
    private String description;//course description
    private String name;//course name, department identifier
    private int num; //course number, used for upper division identification
    private int credits;//credits satisfied by the course
    private int semestersOffered;//integer representing combination of semesesters when the course is offered
    private static EntityManager em;//JPA interaction
    private static EntityManagerFactory emf;


    public Course() {}
    Course(String dept, int level, Grade minGrade, int creds, int offered) {
        prereqs=new ArrayList<CourseGroup>();
        UseLogger logger = new UseLogger();
        id=dept+" "+level;
        name=dept;
        this.minGrade=minGrade;
        credits=creds;
        semestersOffered=offered;
        num=level;
    }
   public void addPreReq(CourseGroup c) {prereqs.add(c);}
   
    public void setPrereqs(Collection<CourseGroup> p) {prereqs=p;}
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name=name;}
    public void setNum(int num) {this.num=num; em.merge(this);}
    public void setDescription(String d){description=d;}
    public void setCredits(int c) {credits=c;}
    public void setSemestersOfferd(int o) {semestersOffered=0;}

    public String getName() {return name;}
    public int getNum(){return num;}
    public String getId() {return id;}
    public Collection<CourseGroup> getPrereqs() {return prereqs;}
    public Grade getMinGrade() {return minGrade;}
    public String getDescription() {return description;}
    public int getCredits() {return credits;}
    public int getSemestersOffered(){return semestersOffered;}
    public boolean isUpperDivision() {return num>=300;}
    //public boolean passedCourse(User u){return u.getRecords().get(id).coursePassed();}

    public RootlessTree<Course> getShortestPrereqPath() {
        
        RootlessTree<Course> tree=new RootlessTree<Course>();
        tree.addRoot(this);
        for(CourseGroup g : prereqs) {
            tree.addTree(g.getTopPrereqPaths(), this);
        }
        
        return tree;
    }
    
    public boolean equals(Course c) {
        return c.getId().equals(id);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Vector;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
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
public class Course implements Serializable, Comparable {
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
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
    private OfferingList semestersOffered;//integer representing combination of semesesters when the course is offered


    public Course() {}
    Course(String dept, int level, Grade minGrade, int creds, OfferingList offered) {
        prereqs=new ArrayList<CourseGroup>();
        UseLogger logger = new UseLogger();
        id=dept+" "+level;
        name=dept;
        this.minGrade=minGrade;
        credits=creds;
        semestersOffered=offered;
        num=level;
    }
    public boolean canTake(Semester s) {
        return semestersOffered.isOffered(s);
    }
    public void addPreReq(CourseGroup c) {
        if(prereqs==null){
            prereqs=new ArrayList<CourseGroup>();
        }
        prereqs.add(c);
    }
    public boolean prereqsComplete(TreeMap<String,CourseRecord> completed, int credits) {
        if(School.getSchool().getStanding(credits)<minLevel) {
            return false;
        }
        for(CourseGroup g : prereqs) {
            if(g.numRemainingCourses(completed)>0) {
                return false;
            }
        }
        return true;
    }
    public void setPrereqs(Collection<CourseGroup> p) {prereqs=p;}
    public void setId(String id) {this.id = id;}
    public void setName(String name) {this.name=name;}
    public void setNum(int num) {this.num=num;}
    public void setDescription(String d){description=d;}
    public void setCredits(int c) {credits=c;}
    public void setSemestersOfferd(OfferingList o) {semestersOffered=o;}

    public String getName() {return name;}
    public int getNum(){return num;}
    public String getId() {return id;}
    public Collection<CourseGroup> getPrereqs() {return prereqs;}
    public Grade getMinGrade() {return minGrade;}
    public String getDescription() {return description;}
    public int getCredits() {return credits;}
    public OfferingList getSemestersOffered(){return semestersOffered;}
    public boolean isUpperDivision() {return num>=300;}
    public int getMinLevel() {return minLevel;}
    public void setMinLevel(int m) {minLevel=m;}
    //public boolean passedCourse(User u){return u.getRecords().get(id).coursePassed();}

    public RootlessTree<Course> getShortestPrereqPath(TreeMap<String,CourseRecord> records) {
        
        RootlessTree<Course> tree=new RootlessTree<Course>();
        tree.addRoot(this);
        for(CourseGroup g : prereqs) {
            tree.addTree(g.getTopPrereqPaths(records), this);
        }
        
        return tree;
    }
    
    public boolean equals(Course c) {
        return c.getId().equals(id);
    }
    public String toString() {
        return getId();
    }

    @Override
    public int compareTo(Object o) {
       return id.compareTo(((Course)o).getId());
    }
}

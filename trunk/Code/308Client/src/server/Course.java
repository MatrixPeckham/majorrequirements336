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
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.criteria.Fetch;
import org.eclipse.persistence.internal.oxm.schema.model.All;

/**
 *
 * @author tj
 */
@Entity
public class Course implements Serializable {

    private Collection<Course> prereqs;
    
    private String id;
    private String minGrade;
    private String description;
    private String name;
    private int num;
    private int credits;
    private int semestersOffered;


    Course() {
    }
    Course(String dept, int level) {
        id=dept+""+level;
        name=dept;

        num=level;
    }
   
  
    public void addCourse(Course c) {
        //em.persist(c);
    }
    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {

    }
    public String getName() {return name;}
    public void setNum(int num) {this.num=num;}
    public int getNum(){return num;}
    @OneToMany()
    public Collection<Course> getPrereqs() {
        return prereqs;
    }
    public void setPrereqs(Collection<Course> p) {
        prereqs=p;
    }
    public void addPreReq(Course c) {

    }
    public boolean passedCourse(User u){return false;}
}

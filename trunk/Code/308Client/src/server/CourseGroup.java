/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author tj
 */
@Entity
public class CourseGroup implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double minGPA;
    private Collection<Course> courses;
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
    @OneToMany
    public Collection<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> a) {
        courses=a;
    }


    

}

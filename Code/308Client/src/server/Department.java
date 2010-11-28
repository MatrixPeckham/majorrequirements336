/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
/**
 *
 * @author Bill
 */
@Entity
public class Department implements Serializable{
    
    @Id
    private String name;
    @OneToMany(fetch=FetchType.EAGER)
    private Collection<Course> courses;
    @OneToMany(fetch=FetchType.EAGER)
    private Collection<Major> majors;
    public Department() {
        courses=new ArrayList<Course>();
        majors=new ArrayList<Major>();
    }
    Department(String name) {
        courses=new ArrayList<Course>();
        majors=new ArrayList<Major>();
        this.name=name;
    }
    public void addCourse(Course c) {
        courses.add(c);
    }
    public void removeCourse(String name) {
        courses.remove(name);
    }
    public void addMajor(Major c) {
        majors.add(c);
    }
    public void removeMajor(String name) {
        majors.remove(name);
    }
    public String getName() {return name;}
    public void setName(String n) {name=n;}
    public ArrayList<Course> getCourses() {
        return new ArrayList<Course>(courses);
    }
    public ArrayList<Major> getMajors() {
        return new ArrayList<Major>(majors);
    }

    public Major findMajor(String name) {
        for(Major m: majors) {
            if(m.getId().equals(name)) {
                return m;
            }
        }
        return null;
    }
    public Course findCourse(String name) {
        for(Course m: courses) {
            if(m.getId().equals(name)) {
                return m;
            }
        }
        return null;}
}

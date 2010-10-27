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
public class Requirement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private int numberOfCourses;
    private double minGPA;
    private int minUpperDivCredits;
    private int minResidentCredits;
    private Collection<CourseGroup> possibleCourses;
    @OneToMany
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
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author TJ
 */
@Entity
public class Grade implements Serializable{
    private double gradePoints;
    
    private String grade;

    public Grade() {

    }
    Grade(double gradePoint) {
        gradePoints=gradePoint;
        if(gradePoint<1.0) {
            grade="F";
        } else if(gradePoint<1.33) {
            grade="D";
        } else if(gradePoint<1.67) {
            grade="D+";
        } else if(gradePoint<2.0) {
            grade="C-";
        } else if(gradePoint<2.33) {
            grade="C";
        } else if(gradePoint<2.67) {
            grade="C+";
        } else if(gradePoint<3.0) {
            grade="B-";
        } else if(gradePoint<3.33) {
            grade="B";
        } else if(gradePoint<3.67) {
            grade="B+";
        } else if(gradePoint<=4.0) {
            grade="A-";
        } else {
            grade="A";
        }
    }
    public Grade(String grade) {
        this.grade=grade;
        if(grade.equals("A")) {
            gradePoints=4;
        } else if(grade.equals("A-")) {
            gradePoints=3.67;
        } else if(grade.equals("B+")) {
            gradePoints=3.33;
        } else if(grade.equals("B")) {
            gradePoints=3;
        } else if(grade.equals("B-")) {
            gradePoints=2.67;
        } else if(grade.equals("C+")) {
            gradePoints=2.33;
        } else if(grade.equals("C")) {
            gradePoints=2;
        } else if(grade.equals("C-")) {
            gradePoints=1.67;
        } else if(grade.equals("D+")) {
            gradePoints=1.33;
        } else if(grade.equals("D")) {
            gradePoints=1;
        } else if(grade.equals("F")) {
            gradePoints=0;
        }
    }
    public Grade(String s, double gp) {
        grade=s;
        gradePoints=gp;
    }
    public double getGradePoints() {return gradePoints;}
    @Id
    public String getGrade() {return grade;}
     public void setGradePoints(int g) {gradePoints=g;}
      public void setGrade(String g) {grade=g;}
        public boolean greaterThan(Grade g) {

            return gradePoints>=g.getGradePoints();
        }
}

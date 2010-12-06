/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import javax.persistence.*;
import persistence.Updater;

/**
 *
 * @author TJ
 */
@Entity
public class Grade implements Serializable, Updater{
    @Id
    private double gradePoints;
    
    private String grade;
   
    public Grade() {

    }
    public Grade(double gradePoint) {
        gradePoints=gradePoint;
        grade=convertGradePoints(gradePoint);
    }
    public Grade(String grade) {
        this.grade=grade;
        gradePoints=convertGradePoints(grade);
    }
    public Grade(String s, double gp) {
        grade=s;
        gradePoints=gp;
    }
    public boolean equals(String a) {
        return grade.equals(a);
    }
   public boolean equals(Object o) {
        Grade g=(Grade)o;
        return gradePoints==g.getGradePoints() && g.getGrade().equals(grade);
    }
    public double getGradePoints() {return gradePoints;}
    
    public String getGrade() {return grade;}
     public void setGradePoints(double g) {gradePoints=g;}
      public void setGrade(String g) {grade=g;
      gradePoints=convertGradePoints(g);
      }
      private double convertGradePoints(String grade) {
          double gradePoints=0;
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
        } else if(grade.equals("I")) {
            gradePoints=0;
        }
          return gradePoints;
      }
      public String convertGradePoints(double gradePoint) {
          String grade="I";
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
          return grade;
      }
        public boolean greaterThan(Grade g) {

            return gradePoints>=g.getGradePoints();
        }
        @Override
        public String toString(){
            return grade;
        }

    @Override
    public void update(Object toUpdate) {
        Grade g=(Grade) toUpdate;
        g.setGrade(grade);
        g.setGradePoints(gradePoints);
    }
}

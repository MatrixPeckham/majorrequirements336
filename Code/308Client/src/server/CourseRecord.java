/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

/**
 *
 * @author TJ
 */
public class CourseRecord implements Serializable{
    private Course course;
    private TreeMap<Semester, Grade> grades;
    //private Vector<Grade> grades;
    private boolean transfer;
    public CourseRecord(Course course, Grade grade, boolean transfer) {
        //grades=new Vector<Grade>();
        grades=new TreeMap<Semester, Grade>();
        addGrade(grade, Semester.freeSemester());
        this.course=course;
        this.transfer=transfer;
    }

    //public TreeMap<Semester, Grade> getGradeTreeMap() { return grades; }
    public Semester getSemester(Grade g) {
           for(Semester s : grades.keySet())
           {
               if(grades.get(s).equals(g))
               {
                   return s;
               }
           }
           return null;
    }
    
    public boolean getTransfer() {return transfer;}
    public Course getCourse() {return course;}
    public boolean coursePassed() {
        for(Grade g : grades.values()) {
            if(g.greaterThan(course.getMinGrade())) {return true;}
        }
        return false;
    }
    public void addGrade(Grade g, Semester s) {grades.put(s,g);}
    public Vector<Grade> getGrades() {return new Vector(grades.values());}
    public double getGPA() {
        double d=0;
        Iterator<Grade> g=grades.values().iterator();
        while(g.hasNext()) {
            d+=g.next().getGradePoints();
        }
        return d/grades.size();
    }
}

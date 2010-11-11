/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.Vector;

/**
 *
 * @author TJ
 */
public class CourseRecord {
    private Course course;
    private Vector<Grade> grades;
    private boolean transfer;
    CourseRecord(Course course, Grade grade, boolean transfer) {
        grades=new Vector<Grade>();
        addGrade(grade);
        this.course=course;
        this.transfer=transfer;
    }
    public Course getCourse() {return course;}
    public boolean coursePassed() {
        for(Grade g : grades) {
            if(g.greaterThan(course.getMinGrade())) {return true;}
        }
        return false;
    }
    public void addGrade(Grade g) {grades.add(g);}
    public Vector<Grade> getGrades() {return grades;}
}

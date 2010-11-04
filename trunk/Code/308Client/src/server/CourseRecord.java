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

    }
    public void addGrade(Grade g) {grades.add(g);}
    public Vector<Grade> getGrades() {return grades;}
}

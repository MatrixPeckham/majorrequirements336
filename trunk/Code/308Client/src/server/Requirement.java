/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import logging.UseLogger;
import java.util.*;
import javax.persistence.FetchType;
import structures.RootlessTree;

/** Requirements that need to be fullfiledd for a major for graduation
 *
 * @author tj
 */
@Entity
public class Requirement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;//(Primary key) name identifier for requirement
    private int numberOfCourses;/* min number of course groups to complete to satisfy requirement*/
    private int minCredits;
    private double minGPA;//min Gpa for passing requirement
    private int minUpperDivCredits;// minimum upper divesion credits required
    private int minResidentCredits;//minumum non-transfer credits required
    private int version;//year of requirement
    public Requirement() {
        possibleCourses=new ArrayList<CourseGroup>();
    }

    @OneToMany(fetch=FetchType.EAGER)
    private Collection<CourseGroup> possibleCourses;
    
    public Collection<CourseGroup> getPossibleCourses() {
        return possibleCourses;
    }
    public void setPossibleCourses(ArrayList<CourseGroup> c) {
        possibleCourses=c;
    }
    public int getVersion(){return version;}
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
    public void setMinGPA(double c) {
        minGPA=c;
    }
    public void setMinGPA(Grade c) {
        setMinGPA(c.getGradePoints());
    }
    public void setMinUpperDivCredits(int c) {
        this.minUpperDivCredits=c;
    }
    public void setMinResidentCredits(int c) {
        minResidentCredits=c;
    }
    public int getYear() {return version;}
    public void setYear(int year) {this.version=year;}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    /**
     * Adds set of required courses to requirement
     * @param c
     */
    public void addCourseGroup(CourseGroup c) {
        try {
            //em.merge(c);
            possibleCourses.add(c);
            //em.merge(this);
            //UseLogger.info("Course Goup added to Requirement "+id);
        } catch(Exception e) {
            UseLogger.severe("line 80 Exception! :" +e.getMessage());
        }
    }
    private Vector<CourseGroup> topLeastRemaining(TreeMap<String, CourseRecord> records){
        Vector<Integer> sizes=new Vector<Integer>();
        int num=numberOfCourses;
        if(num==0 || num>=possibleCourses.size()) {
            return new Vector<CourseGroup>(possibleCourses);
        }
        Vector<CourseGroup> topX=new Vector<CourseGroup>();
        for(CourseGroup g : possibleCourses) {
            Iterator<CourseGroup> it=topX.iterator();
            int j=0;
            boolean flag=false;
            while(it.hasNext() && !flag) {
                CourseGroup g2=it.next();
                if(g.numRemainingCourses(records)<g2.numRemainingCourses(records)) {
                    flag=true;
                    topX.add(j,g);
                }
                j++;
            }
            if(!flag){topX.add(g);}
          
        }
        topX.setSize(this.numberOfCourses);
        return topX;
    }
    public RootlessTree<Course> getRemainingCourses(TreeMap<String, CourseRecord> records) {
        RootlessTree<Course> remaining=new RootlessTree<Course>();
        Vector<CourseGroup> toUse=this.topLeastRemaining(records);
        for(CourseGroup g : toUse) {
            g.getRemainingCourses(records, remaining);
            //remaining.removeDuplicates(c);
        }
        return remaining;
    }

    public RequirementCompletion requirementSatisfied(TreeMap<String, CourseRecord> courses){
        double gpa=0;
        int upper=0;
        int local=0;
        int credits=0;
        int credOp=0;
        int num=0;
        RequirementCompletion rc=new RequirementCompletion();
        rc.complete=true;
        rc.name=this.getId();
        if(possibleCourses.size()==0) {
            for(CourseRecord cr : courses.values()) {
                int cc=cr.getCourse().getCredits();
                for(Grade g : cr.getGrades()) {
                    credOp+=cc;
                    gpa+=g.getGradePoints()*cc;
                }
                if(cr.coursePassed()) {
                    if(!cr.getTransfer()) {
                        local+=cc;
                    }
                    if(cr.getCourse().isUpperDivision()) {
                        upper+=cc;
                    }
                    credits++;
                }
            }
        } else {
        for(CourseGroup cb : possibleCourses) {
            int remCourses=0;
            for(Course c: cb.getCourses()) {
                CourseCompletion c2=new CourseCompletion();
                c2.course=c;
                if(courses.containsKey(c.getId())) {
                    CourseRecord cr=courses.get(c.getId());
                    boolean incomplete=false;
                    for(Grade g : cr.getGrades()) {
                        if(g.getGrade().equals("I")) {
                            incomplete=true;
                        } else {
                        gpa+=g.getGradePoints()*c.getCredits();
                        credOp+=c.getCredits();
                        }
                    }
                    if(incomplete) {
                        c2.message="Class in progress";
                        c2.complete=false;
                        rc.complete=false;
                    } else
                    if(!cr.coursePassed()) {
                        c2.message="Course not passed with minimum grade.";
                        c2.complete=false;
                        rc.complete=false;
                    } else {
                        if(c.isUpperDivision()) {upper+=c.getCredits();}
                        if(!cr.getTransfer()) {local+=c.getCredits();}
                        credits+=c.getCredits();
                        c2.complete=true;
                        remCourses++;
                        c2.message="CourseCompleted";
                    }
                } else {
                    c2.message="Incomplete";
                    c2.complete=false;
                    rc.complete=false;
                }
                rc.courseMessages.add(c2);
            }
            if(remCourses>=cb.getNumReqiured()) {
                num++;
            }
        }
        }
        rc.credsEarned=credits;
        rc.credsTaken=credOp;
        rc.gradePoints=gpa;
        rc.localCreds=local;
        if(credOp>0) {
        gpa=gpa/credOp;
        } else {
            gpa=0;
        }


        rc.message="";
        if(numberOfCourses>0) {
            rc.message+=(num>=numberOfCourses?numberOfCourses:num)+" of "+numberOfCourses+" course sets complete.<br/>";
        }
        rc.message += "GPA: " + gpa + (gpa >= minGPA ? " Meets minimum" : " Does not meet minimum") + "</br>";
        if(minUpperDivCredits>0) {
            rc.message+=upper+" of "+minUpperDivCredits+" required upper division credits completed<br/>";
        }
        if(minCredits>0) {
            rc.message+=credits+" of "+minCredits+" requiredCredits";
        }
        return rc;
    }

}

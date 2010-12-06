/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.io.Serializable;
import java.util.*;
import structures.RootlessTree;
/**
 *
 * @author Bill
 */
public class Schedule implements Serializable{
    private TreeMap<Semester,Vector<Course>>  schedule;

    public Schedule(TreeMap<Semester,Vector<Course>> s) {
        schedule=s;
    }
    public TreeMap<Semester,Vector<Course>> getSchedule() {
        return schedule;
    }
    public static Schedule generateSchedule(User u) {

        Major m=u.getMajor();
        if(m==null) {return null;}
        RootlessTree<Course> t=m.getRemainingCourse((TreeMap<String, CourseRecord>)u.getRecords().clone(), u.getMajorYear());
        Vector<Course> toTake=new Vector<Course>();
        Vector<Course> toRemove=new Vector<Course>();
        Vector<Course> tentative=new Vector<Course>();
        int maxlevel=t.getMaxLevel();
        while(maxlevel>=0) {
            toTake.addAll(t.getDataAtlevel(maxlevel));
            maxlevel--;
        }
        Semester s=Semester.freeSemester();
        int maxSemCredits=School.getSchool().getMaxCreds();
        TreeMap<Semester, Vector<Course>> sched=new TreeMap<Semester, Vector<Course>>();
        TreeMap<String, CourseRecord> added=new TreeMap<String,CourseRecord>();
        int totalCredits=u.getCompletedCredits();
        sched.put(s, new Vector<Course>());
        int tmp=Schedule.getSemesterPlan(s, u, totalCredits, toRemove, toTake, sched.get(s), added);
        maxSemCredits-=tmp;
        totalCredits+=tmp;
        boolean flag=false;
        while(toTake.size()>0) {
            Iterator<Course> it=toTake.iterator();
            boolean found=false;
            while(it.hasNext()) {
                if(maxSemCredits==0) {
                    for(Course c2 : toRemove) {
                        it=toTake.iterator();
                        added.put(c2.getId(), new CourseRecord(c2, new Grade("A"), false));
                        t.removeData(c2);
                    }
                    toRemove.clear();
                    s=s.nextSemester();
                    sched.put(s, new Vector<Course>());
                    maxSemCredits=School.getSchool().getMaxCreds();
                    tmp=Schedule.getSemesterPlan(s, u, totalCredits, toRemove, toTake, sched.get(s), added);
                    maxSemCredits-=tmp;
                    totalCredits+=tmp;
                }
                if(maxSemCredits==0) {continue;}
                Course c=it.next();
                if(c.canTake(s)==1 && c.getCredits()<=maxSemCredits && c.prereqsComplete(added, totalCredits)) {
                    sched.get(s).add(c);
                    
                    toRemove.add(c);
                    totalCredits+=c.getCredits();
                    it.remove();
                    maxSemCredits-=c.getCredits();
                    found=true;
                } else if(c.canTake(s)==0 && c.getCredits()<=maxSemCredits && c.prereqsComplete(added, totalCredits)) {
                    tentative.add(c);
                }
                
            }
            if(maxSemCredits>0) {
                    //try tentative courses now
                    for(Course c2 : tentative) {
                        if(maxSemCredits>=c2.getCredits()) {
                            sched.get(s).add(c2);
                            toRemove.add(c2);
                            totalCredits+=c2.getCredits();
                            toTake.remove(c2);
                            maxSemCredits-=c2.getCredits();
                            found=true;
                        }
                    }
                    tentative=new Vector<Course>();
                    for(Course c2 : toRemove) {
                        added.put(c2.getId(), new CourseRecord(c2, new Grade("A"), false));
                        t.removeData(c2);
                    }
                    toRemove.clear();
                    s=s.nextSemester();
                    sched.put(s, new Vector<Course>());
                    maxSemCredits=School.getSchool().getMaxCreds();
                }
            if(!found) {
                if(flag) {break;}
                flag=true;
            } else {flag=false;}
        }
        return new Schedule(sched);
    }
    private static int getSemesterPlan(Semester s, User u,int cred, Vector<Course> remove,Vector<Course> toTake,Vector<Course> sched, TreeMap<String,CourseRecord> added) {
        TreeMap<String,CourseRecord> records=u.getCourses();
        int credits=0;
        for(CourseRecord r : records.values()) {
            if(r.getIncompleteSemester().equals(s) && r.getCourse().prereqsComplete(added, cred)) {
                sched.add(r.getCourse());

                    remove.add(r.getCourse());

                    toTake.remove(r.getCourse());
                    credits+=r.getCourse().getCredits();
            }
        }
        return credits;
    }

}

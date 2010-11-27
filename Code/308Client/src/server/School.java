/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import persistence.PersistenceManager;
import java.util.*;
/**
 *
 * @author TJ
 */
public class School {
    private TreeMap<String, Department> departments;
    private static School school;
    private static int maxSemesterCreds;
    private static int[] creditThresholds=new int[]{24,57,85};
    private School() {
        departments=new TreeMap<String,Department>();
        maxSemesterCreds=19;
    }
    public int getMaxCreds() {return maxSemesterCreds;}
    public Department getDepartment(String dept) {
        if(dept==null) {return null;}
        if(departments.containsKey(dept)) {
        return departments.get(dept);
        } else {return null;}
    }
    public void addDepartment(Department d) {
        departments.put(d.getName(), d);
        PersistenceManager.merge(d);
    }
    public void removeDepartment(String d) {
        PersistenceManager.remove(departments.remove(d), d);
    }
    public ArrayList<Department> getDepartments() {
        return new ArrayList<Department>(departments.values());
    }
    public static School getSchool() {
        if(school==null) {
            school=new School();
        }
        return school;
    }
    public int getStanding(int credits) {
        for(int i=0; i<creditThresholds.length; i++) {
            if(credits<creditThresholds[i]) {
                return i+1;
            }
        }
        return 4;
    }
    public ArrayList<Major> getAllMajors() {
        ArrayList<Major> a=new ArrayList<Major>();
        for(Department d : departments.values()) {
            a.addAll(d.getMajors());
        }
        return a;
    }
    public static void load() {
     
        School s=getSchool();
        List l=PersistenceManager.executeQuery("Select d from Department d");
        Iterator d=l.iterator();
        while(d.hasNext()) {
            Department dep=(Department) d.next();
            s.addDepartment(dep);
        }
    }
}

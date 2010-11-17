/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.util.*;
/**
 *
 * @author TJ
 */
public class School {
    private TreeMap<String, Department> departments;
    private static School school;

    private School() {
        departments=new TreeMap<String,Department>();
    }
    public Department getDepartment(String dept) {
        if(dept==null) {return null;}
        if(departments.containsKey(dept)) {
        return departments.get(dept);
        } else {return null;}
    }
    public void addDepartment(Department d) {
        departments.put(d.getName(), d);
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
}

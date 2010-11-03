/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.*;
/**
 *
 * @author tj
 */
public class User {
    public static final int STUDENT=0;
    public static final int DEPT_ADMIN=1;
    public static final int SUPER_ADMIN=2;
    private int majorYear;
    private String name;
    private Major major;
    private TreeMap<String, CourseRecord> courses;
    private long userId;
    private int permissions;
    User(long i) {
        userId=i;
        permissions=STUDENT;
        majorYear=(new Date()).getYear();
    }
    public long getID() {return userId;}
 }

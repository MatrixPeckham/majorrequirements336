/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.util.*;
/**
 *
 * @author Bill
 */
public class Schedule {
    private TreeMap<String,Vector<Course>>  schedule;

    public static Schedule generateSchedule(User u) {
        Major m=u.getMajor();

        return new Schedule();
    }
}

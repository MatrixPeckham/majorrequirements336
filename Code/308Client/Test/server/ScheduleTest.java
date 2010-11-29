/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.TreeMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class ScheduleTest {

    public ScheduleTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getSchedule method, of class Schedule.
     */
    @Test
    public void testGetSchedule() {
        System.out.println("getSchedule");
        Schedule instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getSchedule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateSchedule method, of class Schedule.
     */
    @Test
    public void testGenerateSchedule() {
        System.out.println("generateSchedule");
        School.load();
        User u = new User(0);
        u.setMajor(School.getSchool().getDepartment("CSE").findMajor("CSE"));
        Schedule expResult = null;
        Schedule result = Schedule.generateSchedule(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
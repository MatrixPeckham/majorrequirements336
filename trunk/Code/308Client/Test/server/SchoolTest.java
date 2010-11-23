/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.ArrayList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class SchoolTest {

    public SchoolTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getDepartment method, of class School.
     */
    @Test
    public void testGetDepartment() {
        System.out.println("getDepartment");
        String dept = "";
        School instance = null;
        Department expResult = null;
        Department result = instance.getDepartment(dept);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addDepartment method, of class School.
     */
    @Test
    public void testAddDepartment() {
        System.out.println("addDepartment");
        Department d = null;
        School instance = null;
        instance.addDepartment(d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDepartments method, of class School.
     */
    @Test
    public void testGetDepartments() {
        System.out.println("getDepartments");
        School instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.getDepartments();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSchool method, of class School.
     */
    @Test
    public void testGetSchool() {
        System.out.println("getSchool");
        School expResult = null;
        School result = School.getSchool();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of load method, of class School.
     */
    @Test
    public void testLoad() {
        System.out.println("load");
        School.load();
        assertNotNull(School.getSchool().getDepartment("CSE").findMajor("CSE"));
        // TODO review the generated test code and remove the default call to fail.
        
    }

}
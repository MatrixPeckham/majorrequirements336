/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class DepartmentTest {

    public DepartmentTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of addCourse method, of class Department.
     */
    @Test
    public void testAddCourse() {
        System.out.println("addCourse");
        Course c = null;
        Department instance = new Department();
        instance.addCourse(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCourse method, of class Department.
     */
    @Test
    public void testRemoveCourse() {
        System.out.println("removeCourse");
        String name = "";
        Department instance = new Department();
        instance.removeCourse(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addMajor method, of class Department.
     */
    @Test
    public void testAddMajor() {
        System.out.println("addMajor");
        Major c = null;
        Department instance = new Department();
        instance.addMajor(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeMajor method, of class Department.
     */
    @Test
    public void testRemoveMajor() {
        System.out.println("removeMajor");
        String name = "";
        Department instance = new Department();
        instance.removeMajor(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Department.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Department instance = new Department();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Department.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String n = "";
        Department instance = new Department();
        instance.setName(n);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCourses method, of class Department.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        Department instance = new Department();
        ArrayList expResult = null;
        ArrayList result = instance.getCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMajors method, of class Department.
     */
    @Test
    public void testGetMajors() {
        System.out.println("getMajors");
        School.load();
        Department instance = School.getSchool().getDepartment("CSE");
        ArrayList expResult = null;
        ArrayList result = instance.getMajors();
        assertEquals(1, result.size());
    }

    /**
     * Test of findMajor method, of class Department.
     */
    @Test
    public void testFindMajor() {
        System.out.println("findMajor");
        String name = "";
        Department instance = new Department();
        Major expResult = null;
        Major result = instance.findMajor(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findCourse method, of class Department.
     */
    @Test
    public void testFindCourse() {
        System.out.println("findCourse");
        String name = "";
        Department instance = new Department();
        Course expResult = null;
        Course result = instance.findCourse(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
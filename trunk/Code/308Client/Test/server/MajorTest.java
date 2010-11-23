/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.Collection;
import java.util.TreeMap;
import java.util.Vector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import structures.RootlessTree;

/**
 *
 * @author TJ
 */
public class MajorTest {

    public MajorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of main method, of class Major.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Major.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRequirements method, of class Major.
     */
    @Test
    public void testGetRequirements() {
        System.out.println("getRequirements");
        Major instance = new Major();
        Collection expResult = null;
        Collection result = instance.getRequirements();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setRequirements method, of class Major.
     */
    @Test
    public void testSetRequirements() {
        System.out.println("setRequirements");
        Collection<Requirement> r = null;
        Major instance = new Major();
        instance.setRequirements(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Major.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Major instance = new Major();
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Major.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "";
        Major instance = new Major();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRequirement method, of class Major.
     */
    @Test
    public void testAddRequirement() {
        System.out.println("addRequirement");
        Requirement r = null;
        Major instance = new Major();
        instance.addRequirement(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainingCourse method, of class Major.
     */
    @Test
    public void testGetRemainingCourse() throws Exception {
        System.out.println("getRemainingCourse");
        School.load();
        TreeMap<String, CourseRecord> records = new TreeMap<String, CourseRecord>();
        Department d=School.getSchool().getDepartment("CSE");
        Major instance = d.findMajor("CSE");
        RootlessTree expResult = null;
        RootlessTree result = instance.getRemainingCourse(records, 2008);
        System.out.println(result.size());
        assertEquals(result.count(), 0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of requirementsRemaining method, of class Major.
     */
    @Test
    public void testRequirementsRemaining() {
        System.out.println("requirementsRemaining");
        TreeMap<String, CourseRecord> r = null;
        int year = 0;
        Major instance = new Major();
        Vector expResult = null;
        Vector result = instance.requirementsRemaining(r, year);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeRequirement method, of class Major.
     */
    @Test
    public void testRemoveRequirement() {
        System.out.println("removeRequirement");
        Requirement r = null;
        Major instance = new Major();
        instance.removeRequirement(r);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
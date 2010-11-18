/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import structures.RootlessTree;

/**
 *
 * @author TJ
 */
public class RequirementTest {

    public RequirementTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getPossibleCourses method, of class Requirement.
     */
    @Test
    public void testGetPossibleCourses() {
        System.out.println("getPossibleCourses");
        Requirement instance = new Requirement();
        Collection expResult = null;
        Collection result = instance.getPossibleCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPossibleCourses method, of class Requirement.
     */
    @Test
    public void testSetPossibleCourses() {
        System.out.println("setPossibleCourses");
        ArrayList<CourseGroup> c = null;
        Requirement instance = new Requirement();
        instance.setPossibleCourses(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getVersion method, of class Requirement.
     */
    @Test
    public void testGetVersion() {
        System.out.println("getVersion");
        Requirement instance = new Requirement();
        int expResult = 0;
        int result = instance.getVersion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNumberOfCourses method, of class Requirement.
     */
    @Test
    public void testGetNumberOfCourses() {
        System.out.println("getNumberOfCourses");
        Requirement instance = new Requirement();
        int expResult = 0;
        int result = instance.getNumberOfCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinGPA method, of class Requirement.
     */
    @Test
    public void testGetMinGPA() {
        System.out.println("getMinGPA");
        Requirement instance = new Requirement();
        double expResult = 0.0;
        double result = instance.getMinGPA();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinUpperDivCredits method, of class Requirement.
     */
    @Test
    public void testGetMinUpperDivCredits() {
        System.out.println("getMinUpperDivCredits");
        Requirement instance = new Requirement();
        int expResult = 0;
        int result = instance.getMinUpperDivCredits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinResidentCredits method, of class Requirement.
     */
    @Test
    public void testGetMinResidentCredits() {
        System.out.println("getMinResidentCredits");
        Requirement instance = new Requirement();
        int expResult = 0;
        int result = instance.getMinResidentCredits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNumberOfCourses method, of class Requirement.
     */
    @Test
    public void testSetNumberOfCourses() {
        System.out.println("setNumberOfCourses");
        int c = 0;
        Requirement instance = new Requirement();
        instance.setNumberOfCourses(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinGPA method, of class Requirement.
     */
    @Test
    public void testSetMinGPA_double() {
        System.out.println("setMinGPA");
        double c = 0.0;
        Requirement instance = new Requirement();
        instance.setMinGPA(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinGPA method, of class Requirement.
     */
    @Test
    public void testSetMinGPA_Grade() {
        System.out.println("setMinGPA");
        Grade c = null;
        Requirement instance = new Requirement();
        instance.setMinGPA(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinUpperDivCredits method, of class Requirement.
     */
    @Test
    public void testSetMinUpperDivCredits() {
        System.out.println("setMinUpperDivCredits");
        int c = 0;
        Requirement instance = new Requirement();
        instance.setMinUpperDivCredits(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinResidentCredits method, of class Requirement.
     */
    @Test
    public void testSetMinResidentCredits() {
        System.out.println("setMinResidentCredits");
        int c = 0;
        Requirement instance = new Requirement();
        instance.setMinResidentCredits(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getYear method, of class Requirement.
     */
    @Test
    public void testGetYear() {
        System.out.println("getYear");
        Requirement instance = new Requirement();
        int expResult = 0;
        int result = instance.getYear();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setYear method, of class Requirement.
     */
    @Test
    public void testSetYear() {
        System.out.println("setYear");
        int year = 0;
        Requirement instance = new Requirement();
        instance.setYear(year);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Requirement.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Requirement instance = new Requirement();
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Requirement.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "";
        Requirement instance = new Requirement();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCourseGroup method, of class Requirement.
     */
    @Test
    public void testAddCourseGroup() {
        System.out.println("addCourseGroup");
        CourseGroup c = null;
        Requirement instance = new Requirement();
        instance.addCourseGroup(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainingCourses method, of class Requirement.
     */
    @Test
    public void testGetRemainingCourses() throws Exception {
        User u=new User(0);
        u.parseFile(new File("src/Courses.xml"));
        u.parseFile(new File("src/Major.xml"));
        System.out.println("getRemainingCourses");
        TreeMap<String, CourseRecord> records = null;
        RootlessTree<Course> courses = null;
        Requirement instance = new Requirement();
        CourseGroup g=new CourseGroup();
        g.addCourse(School.getSchool().getDepartment("CSE").findCourse("CSE 114"));
        CourseGroup g2=new CourseGroup();
        g2.addCourse(School.getSchool().getDepartment("CSE").findCourse("CSE 214"));
        instance.addCourseGroup(g);
        instance.addCourseGroup(g2);
        RootlessTree<Course> c=instance.getRemainingCourses(new TreeMap<String, CourseRecord>());
        int i=instance.getRemainingCourses(new TreeMap<String, CourseRecord>()).size();
        assertEquals(2, i);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of requirementSatisfied method, of class Requirement.
     */
    @Test
    public void testRequirementSatisfied() {
        System.out.println("requirementSatisfied");
        TreeMap<String, CourseRecord> courses = null;
        Requirement instance = new Requirement();
        boolean expResult = false;
        boolean result = instance.requirementSatisfied(courses);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
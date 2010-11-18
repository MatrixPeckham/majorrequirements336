/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.ArrayList;
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
public class CourseGroupTest {

    public CourseGroupTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getId method, of class CourseGroup.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        CourseGroup instance = new CourseGroup();
        Long expResult = null;
        Long result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class CourseGroup.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Long id = null;
        CourseGroup instance = new CourseGroup();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinGPA method, of class CourseGroup.
     */
    @Test
    public void testGetMinGPA() {
        System.out.println("getMinGPA");
        CourseGroup instance = new CourseGroup();
        double expResult = 0.0;
        double result = instance.getMinGPA();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMinGPA method, of class CourseGroup.
     */
    @Test
    public void testSetMinGPA() {
        System.out.println("setMinGPA");
        double d = 0.0;
        CourseGroup instance = new CourseGroup();
        instance.setMinGPA(d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCourses method, of class CourseGroup.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        CourseGroup instance = new CourseGroup();
        Collection expResult = null;
        Collection result = instance.getCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCourses method, of class CourseGroup.
     */
    @Test
    public void testSetCourses() {
        System.out.println("setCourses");
        ArrayList<Course> a = null;
        CourseGroup instance = new CourseGroup();
        instance.setCourses(a);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addCourse method, of class CourseGroup.
     */
    @Test
    public void testAddCourse() {
        System.out.println("addCourse");
        Course c = null;
        CourseGroup instance = new CourseGroup();
        instance.addCourse(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeCourse method, of class CourseGroup.
     */
    @Test
    public void testRemoveCourse() {
        System.out.println("removeCourse");
        Course c = null;
        CourseGroup instance = new CourseGroup();
        instance.removeCourse(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRemainingCourses method, of class CourseGroup.
     */
    @Test
    public void testGetRemainingCourses() throws Exception {
        User u=new User(0);
        u.parseFile(new File("src/Courses.xml"));
        Course c1=School.getSchool().getDepartment("CSE").findCourse("CSE 219");
        Course c2=School.getSchool().getDepartment("CSE").findCourse("CSE 214");
        Course c3=School.getSchool().getDepartment("CSE").findCourse("CSE 114");
        System.out.println("getRemainingCourses");
        TreeMap<String,CourseRecord> records = new TreeMap<String,CourseRecord>();
        CourseRecord c=new CourseRecord(c2, new Grade(4.0), false);
        records.put("CSE 214", c);
        //records.add(new CourseRecord(new Course("CSE", 300)));
        CourseGroup instance = new CourseGroup();
        instance.addCourse(c1);

        RootlessTree expResult = null;
        expResult=new RootlessTree<Course>();
        expResult.addRoot(c1);
        expResult.addChild(c1, c2);
        expResult.addChild(c2, c3);
        RootlessTree result =instance.getRemainingCourses(records);
        boolean b=result.equals(expResult);
        assertEquals(b, true);
        
    }

    /**
     * Test of calculateGPA method, of class CourseGroup.
     */
    @Test
    public void testCalculateGPA() {
        System.out.println("calculateGPA");

        TreeMap<String, CourseRecord> cr = null;
        CourseGroup instance = new CourseGroup();
        double expResult = 0.0;
        double result = instance.calculateGPA(cr);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of credits method, of class CourseGroup.
     */
    @Test
    public void testCredits() {
        System.out.println("credits");
        TreeMap<String, CourseRecord> cr = null;
        CourseGroup instance = new CourseGroup();
        int expResult = 0;
        int result = instance.credits(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of upperCredits method, of class CourseGroup.
     */
    @Test
    public void testUpperCredits() {
        System.out.println("upperCredits");
        TreeMap<String, CourseRecord> cr = null;
        CourseGroup instance = new CourseGroup();
        int expResult = 0;
        int result = instance.upperCredits(cr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopPrereqPaths method, of class CourseGroup.
     */
    @Test
    public void testGetTopPrereqPaths() throws Exception{
        User u=new User(0);
        u.parseFile(new File("src/Major.xml"));
        u.parseFile(new File("src/Courses.xml"));
        u.setMajor(School.getSchool().getDepartment("CSE").findMajor("CSE"));
        System.out.println("getTopPrereqPaths");
        CourseGroup instance = new CourseGroup();
        Department d=School.getSchool().getDepartment("CSE");
        instance.addCourse(d.findCourse("CSE308"));
        RootlessTree expResult = null;
        RootlessTree result = instance.getTopPrereqPaths();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sequenceDone method, of class CourseGroup.
     */
    @Test
    public void testSequenceDone() {
        System.out.println("sequenceDone");
        TreeMap<String, CourseRecord> rec = null;
        CourseGroup instance = new CourseGroup();
        boolean expResult = false;
        boolean result = instance.sequenceDone(rec);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
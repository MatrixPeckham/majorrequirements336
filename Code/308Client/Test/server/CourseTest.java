/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.Collection;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import structures.RootlessTree;

/**
 *
 * @author TJ
 */
public class CourseTest {

    public CourseTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addPreReq method, of class Course.
     */
    @Test
    public void testAddPreReq() {
        System.out.println("addPreReq");
        CourseGroup c = null;
        Course instance = new Course();
        instance.addPreReq(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPrereqs method, of class Course.
     */
    @Test
    public void testSetPrereqs() {
        System.out.println("setPrereqs");
        Collection<CourseGroup> p = null;
        Course instance = new Course();
        instance.setPrereqs(p);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Course.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String id = "";
        Course instance = new Course();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class Course.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String name = "";
        Course instance = new Course();
        instance.setName(name);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNum method, of class Course.
     */
    @Test
    public void testSetNum() {
        System.out.println("setNum");
        int num = 0;
        Course instance = new Course();
        instance.setNum(num);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDescription method, of class Course.
     */
    @Test
    public void testSetDescription() {
        System.out.println("setDescription");
        String d = "";
        Course instance = new Course();
        instance.setDescription(d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setCredits method, of class Course.
     */
    @Test
    public void testSetCredits() {
        System.out.println("setCredits");
        int c = 0;
        Course instance = new Course();
        instance.setCredits(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSemestersOfferd method, of class Course.
     */
    @Test
    public void testSetSemestersOfferd() {
        System.out.println("setSemestersOfferd");
        int o = 0;
        Course instance = new Course();
        instance.setSemestersOfferd(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class Course.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        Course instance = new Course();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNum method, of class Course.
     */
    @Test
    public void testGetNum() {
        System.out.println("getNum");
        Course instance = new Course();
        int expResult = 0;
        int result = instance.getNum();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Course.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Course instance = new Course();
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPrereqs method, of class Course.
     */
    @Test
    public void testGetPrereqs() {
        System.out.println("getPrereqs");
        Course instance = new Course();
        Collection expResult = null;
        Collection result = instance.getPrereqs();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMinGrade method, of class Course.
     */
    @Test
    public void testGetMinGrade() {
        System.out.println("getMinGrade");
        Course instance = new Course();
        Grade expResult = null;
        Grade result = instance.getMinGrade();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescription method, of class Course.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        Course instance = new Course();
        String expResult = "";
        String result = instance.getDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCredits method, of class Course.
     */
    @Test
    public void testGetCredits() {
        System.out.println("getCredits");
        Course instance = new Course();
        int expResult = 0;
        int result = instance.getCredits();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSemestersOffered method, of class Course.
     */
    @Test
    public void testGetSemestersOffered() {
        System.out.println("getSemestersOffered");
        Course instance = new Course();
        int expResult = 0;
        int result = instance.getSemestersOffered();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isUpperDivision method, of class Course.
     */
    @Test
    public void testIsUpperDivision() {
        System.out.println("isUpperDivision");
        Course instance = new Course();
        boolean expResult = false;
        boolean result = instance.isUpperDivision();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of passedCourse method, of class Course.
     */
    @Test
    public void testPassedCourse() {
        System.out.println("passedCourse");
        User u = null;
        Course instance = new Course();
        boolean expResult = false;
        boolean result = instance.passedCourse(u);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getShortestPrereqPath method, of class Course.
     */
    @Test
    public void testGetShortestPrereqPath() throws Exception {
        System.out.println("getShortestPrereqPath");
        User u=new User(0);
        u.parseFile(new File("src/Courses.xml"));
        Course instance = School.getSchool().getDepartment("CSE").findCourse("CSE 219");
        RootlessTree expResult = null;
        RootlessTree result = instance.getShortestPrereqPath();

        assertEquals(result.getMaxLevel(), 2);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Course.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Course c = null;
        Course instance = new Course();
        boolean expResult = false;
        boolean result = instance.equals(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
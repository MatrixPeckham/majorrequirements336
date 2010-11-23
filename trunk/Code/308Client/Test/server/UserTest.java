/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class UserTest {

    public UserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getMajor method, of class User.
     */
    @Test
    public void testGetMajor() {
        System.out.println("getMajor");
        User instance = null;
        Major expResult = null;
        Major result = instance.getMajor();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class User.
     */
    @Test
    public void testGetID() {
        System.out.println("getID");
        User instance = null;
        long expResult = 0L;
        long result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of generateSchedule method, of class User.
     */
    @Test
    public void testGenerateSchedule() {
        System.out.println("generateSchedule");
        User instance = null;
        Schedule expResult = null;
        Schedule result = instance.generateSchedule();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseFile method, of class User.
     */
    @Test
    public void testParseFile() throws Exception {
        User u=new User(User.SUPER_ADMIN);
        File f=new File(".\\src\\Courses.xml");
        System.out.println("parseFile");
        u.parseFile(f);
        u.parseFile(new File(".\\src\\Major.xml"));
        u.parseFile(new File(".\\src\\CoursesTaken.xml"));
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeFile method, of class User.
     */
    @Test
    public void testWriteFile() {
        System.out.println("writeFile");
        String cmd = "";
        User instance = null;
        File expResult = null;
        File result = instance.writeFile(cmd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.TreeMap;
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
public class UserTest {

    public UserTest() {
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
     * Test of getRecords method, of class User.
     */
    @Test
    public void testGetRecords() {
        System.out.println("getRecords");
        User instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getRecords();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
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
     * Test of getName method, of class User.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        User instance = null;
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMajor method, of class User.
     */
    @Test
    public void testSetMajor() {
        System.out.println("setMajor");
        Major m = null;
        User instance = null;
        instance.setMajor(m);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMajorYear method, of class User.
     */
    @Test
    public void testGetMajorYear() {
        System.out.println("getMajorYear");
        User instance = null;
        int expResult = 0;
        int result = instance.getMajorYear();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPermissions method, of class User.
     */
    @Test
    public void testGetPermissions() {
        System.out.println("getPermissions");
        User instance = null;
        int expResult = 0;
        int result = instance.getPermissions();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCourses method, of class User.
     */
    @Test
    public void testGetCourses() {
        System.out.println("getCourses");
        User instance = null;
        TreeMap expResult = null;
        TreeMap result = instance.getCourses();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUserId method, of class User.
     */
    @Test
    public void testSetUserId() {
        System.out.println("setUserId");
        Long id = null;
        User instance = null;
        instance.setUserId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setName method, of class User.
     */
    @Test
    public void testSetName() {
        System.out.println("setName");
        String s = "";
        User instance = null;
        instance.setName(s);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPermissions method, of class User.
     */
    @Test
    public void testSetPermissions() {
        System.out.println("setPermissions");
        int i = 0;
        User instance = null;
        instance.setPermissions(i);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setMajorYear method, of class User.
     */
    @Test
    public void testSetMajorYear() {
        System.out.println("setMajorYear");
        int i = 0;
        User instance = null;
        instance.setMajorYear(i);
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
        System.out.println("parseFile");
        File f = null;
        User instance = null;
        instance.parseFile(f);
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

    /**
     * Test of getTextOfFile method, of class User.
     */
    @Test
    public void testGetTextOfFile() {
        System.out.println("getTextOfFile");
        String cmd = "";
        User instance = null;
        String expResult = "";
        String result = instance.getTextOfFile(cmd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
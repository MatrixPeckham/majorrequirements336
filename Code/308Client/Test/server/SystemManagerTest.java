/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class SystemManagerTest {

    public SystemManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of getSystemManager method, of class SystemManager.
     */
    @Test
    public void testGetSystemManager() {
        System.out.println("getSystemManager");
        SystemManager expResult = null;
        SystemManager result = SystemManager.getSystemManager();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkLogin method, of class SystemManager.
     */
    @Test
    public void testCheckLogin() {
        System.out.println("checkLogin");
        String[] user = {"TJ"};
        String[] pass = {"password"};
        SystemManager instance = SystemManager.getSystemManager();
        int expResult = 2;
        for(String u : user) {
            for(String p: pass) {
                int result = instance.checkLogin(u, p);
                assertEquals(expResult, result);
            }
        }
        
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addUser method, of class SystemManager.
     */
    @Test
    public void testAddUser() {
        System.out.println("addUser");
        SystemManager instance = null;
        User expResult = null;
        User result = instance.addUser();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
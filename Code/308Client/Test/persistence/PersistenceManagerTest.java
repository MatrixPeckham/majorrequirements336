/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistence;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class PersistenceManagerTest {

    public PersistenceManagerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of executeQuery method, of class PersistenceManager.
     */
    @Test
    public void testExecuteQuery() {
        System.out.println("executeQuery");
        String q = "";
        List expResult = null;
        List result = PersistenceManager.executeQuery(q);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of persist method, of class PersistenceManager.
     */
    @Test
    public void testPersist() {
        System.out.println("persist");
        Object o = null;
        PersistenceManager.persist(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of merge method, of class PersistenceManager.
     */
    @Test
    public void testMerge() {
        System.out.println("merge");
        Object o = null;
        PersistenceManager.merge(o);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
package structures;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;
import java.util.Vector;
import structures.RootlessTree;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TJ
 */
public class RootlessTreeTest {

    public RootlessTreeTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     * Test of addRoot method, of class RootlessTree.
     */
    @Test
    public void testAddRoot() {
        System.out.println("addRoot");
        String data = "A";
        RootlessTree instance = new RootlessTree<String>();
        instance.addRoot(data);
         String parent = "A";
        String childData = "B";
        //RootlessTree instance = new RootlessTree();
        instance.addChild(parent, childData);
        assertEquals(instance.size(),2);
       assertEquals(instance.dataExists("B"),true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addChild method, of class RootlessTree.
     */
    @Test
    public void testAddChild() {
        System.out.println("addChild");
        String parent = "A";
        String childData = "B";
        RootlessTree instance = new RootlessTree();
        instance.addChild(parent, childData);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of removeData method, of class RootlessTree.
     */
    @Test
    public void testRemoveData() {
        System.out.println("removeData");
        Object data = null;
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.removeData(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTreeWithRoot method, of class RootlessTree.
     */
    @Test
    public void testGetTreeWithRoot() {
        System.out.println("getTreeWithRoot");
        int index = 0;
        RootlessTree instance = new RootlessTree();
        RootlessTree expResult = null;
        RootlessTree result = instance.getTreeWithRoot(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxLevel method, of class RootlessTree.
     */
    @Test
    public void testGetMaxLevel_0args() {
        System.out.println("getMaxLevel");
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.getMaxLevel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMaxLevel method, of class RootlessTree.
     */
    @Test
    public void testGetMaxLevel_int() {
        System.out.println("getMaxLevel");
        int i = 0;
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.getMaxLevel(i);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of size method, of class RootlessTree.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.size();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addChildren method, of class RootlessTree.
     */
    @Test
    public void testAddChildren() {
        System.out.println("addChildren");
        Object parent = null;
        Collection<T> childData = null;
        RootlessTree instance = new RootlessTree();
        instance.addChildren(parent, childData);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSubtree method, of class RootlessTree.
     */
    @Test
    public void testGetSubtree() {
        System.out.println("getSubtree");
        int index = 0;
        RootlessTree instance = new RootlessTree();
        RootlessTree expResult = null;
        RootlessTree result = instance.getSubtree(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dataExists method, of class RootlessTree.
     */
    @Test
    public void testDataExists() {
        System.out.println("dataExists");
        Object data = null;
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.dataExists(data);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeParent method, of class RootlessTree.
     */
    @Test
    public void testChangeParent() {
        System.out.println("changeParent");
        Object data = null;
        Object newParent = null;
        RootlessTree instance = new RootlessTree();
        instance.changeParent(data, newParent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of numRoots method, of class RootlessTree.
     */
    @Test
    public void testNumRoots() {
        System.out.println("numRoots");
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.numRoots();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRoots method, of class RootlessTree.
     */
    @Test
    public void testGetRoots() {
        System.out.println("getRoots");
        RootlessTree instance = new RootlessTree();
        Vector expResult = null;
        Vector result = instance.getRoots();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTree method, of class RootlessTree.
     */
    @Test
    public void testAddTree_RootlessTree() {
        System.out.println("addTree");
        RootlessTree<String> t = new RootlessTree();
        t.addRoot("a");
        t.addRoot("b");
        RootlessTree<String> instance = new RootlessTree();
        instance.addRoot("c");
        instance.addChild("c", "a");
        RootlessTree<String> expResult=new RootlessTree();
        expResult.addRoot("b");
        expResult.addChild("b", "c");
        expResult.addChild("c", "a");
        t.addTree(instance, "b");
        assertEquals(expResult.equals(t),true);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addTree method, of class RootlessTree.
     */
    @Test
    public void testAddTree_RootlessTree_GenericType() {
        System.out.println("addTree");
        RootlessTree<T> t = null;
        Object parent = null;
        RootlessTree instance = new RootlessTree();
        instance.addTree(t, parent);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class RootlessTree.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        RootlessTree<String> a = new RootlessTree<String>();
        a.addRoot("a");
        RootlessTree<String> instance = new RootlessTree<String>();
        instance.addRoot("a");
        boolean expResult = true;
        boolean result = instance.equals(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of upadteLevels method, of class RootlessTree.
     */
    @Test
    public void testUpadteLevels() {
        System.out.println("upadteLevels");
        RootlessTree instance = new RootlessTree();
        instance.upadteLevels();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergeTrees method, of class RootlessTree.
     */
    @Test
    public void testMergeTrees() {
        System.out.println("mergeTrees");
        RootlessTree to = null;
        RootlessTree addition = null;
        Object parent = null;
        int level = 0;
        RootlessTree.mergeTrees(to, addition, parent, level);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of count method, of class RootlessTree.
     */
    @Test
    public void testCount() {
        System.out.println("count");
        RootlessTree instance = new RootlessTree();
        int expResult = 0;
        int result = instance.count();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
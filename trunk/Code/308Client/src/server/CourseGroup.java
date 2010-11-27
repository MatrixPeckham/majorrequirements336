/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import structures.*;

/**Course Group
 * TRhis class represents a sequence of courses
 * all courses in a course group must be completed
 * @author tj
 */

@Entity
public class CourseGroup implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;//a unique number for the course sequence
    private double minGPA;//minGPA to complete the sequence successfully
    @OneToMany(fetch=FetchType.EAGER)
    private Collection<Course> courses;//Required courses- maps to courses in course table
    private int required;
     private static EntityManager em;
    private static EntityManagerFactory emf;

    public CourseGroup() {
        courses=new ArrayList();
    }
    public Long getId() {
        return id;
    }
    public void setNumReqiured(int id) {
        required = id;
    }
    public int getNumReqiured() {
        return required;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public double getMinGPA() {
        return minGPA;
    }
    public void setMinGPA(double d) {
        minGPA=d;
    }
    public Collection<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> a) {
        courses=a;
    }
    public double calculateGPA(TreeMap<String, CourseRecord> cr) {
         double gpa=0;
         int count=0;
         for(Course c : courses) {
            CourseRecord r=cr.get(c.getId());
            if(r!=null) {
                gpa+=r.getGPA();
                count++;
            }
        }
        return gpa/count;
    }
    public int credits(TreeMap<String, CourseRecord> cr) {
        int creds=0;
        for(Course c : courses) {
            CourseRecord r=cr.get(c.getId());
            if(r!=null && r.coursePassed()) {
                creds+=c.getCredits();
            }
        }
        return creds;
    }
    public int upperCredits(TreeMap<String, CourseRecord> cr) {
        int creds=0;
        for(Course c : courses) {
            CourseRecord r=cr.get(c.getId());
            if(r!=null && r.coursePassed() && c.isUpperDivision()) {
                creds+=c.getCredits();
            }
        }
        return creds;
    }
    /**
     * Adds Course and persists sequence in database
     * @param c
     */
    public void addCourse(Course c) {
        //em.merge(c);
        courses.add(c);
        //em.merge(this);
        //em.getTransaction().commit();
    }
    /**
     * Removes Course and persists sequence in database
     * @param c
     */
    public void removeCourse(Course c) {
        em.getTransaction().begin();
        courses.remove(c);
        em.merge(this);
        em.getTransaction().commit();
    }
    public void getRemainingCourses2(TreeMap<String,CourseRecord> records) {
        for(Course c : courses) {
            if(records.containsKey(c.getId()) && records.get(c.getId()).coursePassed()) {

            }
        }
    }
    public void getRemainingCourses(TreeMap<String,CourseRecord> records, RootlessTree<Course> have) {
        if(have==null){
            have=new RootlessTree<Course>();
        }
        have.addTree(getRemainingCourses(records));
    }
    public RootlessTree<Course> getRemainingCourses(TreeMap<String,CourseRecord> records) {
        RootlessTree<Course> tree=new RootlessTree<Course>();
        getRemainingCourses(records, tree, 0, null);
        return tree;
    }
    public Vector<Course> remainingCoursesNoPrereq(TreeMap<String, CourseRecord> records) {
        Vector<Course> remain=new Vector<Course>();
        for(Course c : courses) {
            if(!records.containsKey(c.getId())) {
                remain.add(c);
            }
        }
        return remain;
    }
    public int numRemainingCourses(TreeMap<String, CourseRecord> records) {
        int rem=required;
        if(required==0) {rem=courses.size();}
        for(Course c : courses) {
            if(records.containsKey(c.getId())) {
                rem--;
            }
        }

        return rem<0?0:rem;
    }
    private void getRemainingCourses(TreeMap<String, CourseRecord> records, RootlessTree<Course> course, int level, Course parent) {
        int numrequired=courses.size();

        /* IDEA
         * 1.) Go Through courses
         * For Each Course...
         * i.) if course is completed, all prereqs must be completed, neglect course
         * and its prereqs
         * ii.) it may also be a prereq for  a course that already exists in the tree.
         * In that case dont add it
         * we want to make sure that the student dosnt have to wait to take it, so make tree as deep
         * as possible. So, if the courses level in the tree is greater than the course it would be added under
         * move the entire branch
         * iii.) if it dosnt exist, all we have to do is add the course and do the repeat the process
         * for all prereqs
         */

        for(Course c : courses) {
            int i;
            CourseRecord r=records.get(c.getId());
            if(r!=null && r.coursePassed()) {
                numrequired-=1;
            } else if((i=course.dataExists(c))!=-1) {
                if(level>=i) {
                    course.changeParent(c, parent);
                }
            } else {
                course.addChild(parent, c);
                //getRemainingCourses(records, c.getShortestPrereqPath().getSubtree(0), level+1, c);
                RootlessTree.mergeTrees(course, c.getShortestPrereqPath(records).getSubtree(0), c, level+1);
            }
        }
    }
    
    public RootlessTree<Course> getTopPrereqPaths(TreeMap<String,CourseRecord> records) {
        RootlessTree<Course> c=new RootlessTree<Course>();
        /* IDEA
         * 1.) Get all prereq paths
         * 2.) Get the top x shortest paths (where x represents number of required courses
         * to complete course sequence
         */
        float maxPath=Float.POSITIVE_INFINITY;
        for(Course c2 : courses) {
            CourseRecord r=records.get(c2.getId());
            if(r!=null && r.coursePassed()) {
                return new RootlessTree<Course>();
            }
            RootlessTree<Course> tmp=c2.getShortestPrereqPath(records);
            if(tmp.getMaxLevel()<maxPath) {c=tmp;}
        }
        return c;
    }
    public boolean sequenceDone(TreeMap<String, CourseRecord> rec) {
        int num=0;
        for(Course r :courses) {
            CourseRecord cr=rec.get(r.getId());
            if(cr==null || !cr.coursePassed()) {
                num++;
            }
        }
        return num==0;
    }

}

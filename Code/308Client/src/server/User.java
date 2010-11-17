/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.File;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
/**
 *
 * @author tj
 */
public class User implements Scheduler, FileParser{
    public static final int STUDENT=0;
    public static final int DEPT_ADMIN=1;
    public static final int SUPER_ADMIN=2;
    private int majorYear;
    private String name;
    private Major major;
    private TreeMap<String, CourseRecord> courses;
    private long userId;
    private int permissions;
    User(long i) {
        courses=new TreeMap<String, CourseRecord>();
        userId=i;
        permissions=STUDENT;
        majorYear=(new Date()).getYear();
    }
    public TreeMap<String, CourseRecord> getRecords() {return courses;}
    public Major getMajor() {return major;}
    public long getID() {return userId;}
    public String getName() {return name;}
    public void setMajor(Major m) {major=m;}
    public int getMajorYear() {return majorYear;}
    public int getPermissions() {return permissions;}
    public TreeMap<String, CourseRecord> getCourses() {return courses;}

    public void setMajor(Major m) {major = m;}
    public void setUserId(Long id) {userId = id;}
    public void setName(String s) {name = s;}
    public void setPermissions(int i) {permissions = i;}
    public void setMajorYear(int i) {majorYear = i;}

    @Override
    public Schedule generateSchedule() {
        //initiate values for starting semester;
            //1->Fall
            //2->Spring
        // well use bitwise ops to compare for course semseter offered
        int semester=1;
        int year=2010;
        int maxCredits=17; //info will be received fromm school object
        //First get all remaining Courses
        //Vector<Course> remaining=major.getRemainingCourse(courses.values());
        return new Schedule();
    }

    @Override
    public void parseFile(File f) throws Exception{
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        
        DocumentBuilder db= dbf.newDocumentBuilder();

        Document d=db.parse(f);
        
        Element fileElement=d.getDocumentElement();

        String fileType=fileElement.getAttribute("type");
        if(fileType.equals("majorRequirement")) {
            NodeList majors=fileElement.getElementsByTagName("major");
            for(int i=0; i<majors.getLength(); i++) {
                fileElement=(Element) majors.item(i);
                String dept=fileElement.getElementsByTagName("department").item(0).getTextContent();
                major=parseMajorFile(fileElement);
                School.getSchool().getDepartment(dept).addMajor(major);
            }
        } else if(fileType.equals("courses")) {
           // if(permissions!=this.SUPER_ADMIN) {return;}
            NodeList depts=fileElement.getElementsByTagName("department");
            for(int i=0; i<depts.getLength(); i++) {
                String dept=((Element)((Element)depts.item(i)).getElementsByTagName("name").item(0)).getTextContent();
                parseCourses((Element)depts.item(i), dept);
            }
        } else if(fileType.equals("record")) {
            NodeList course=fileElement.getElementsByTagName("course");
            for(int i=0; i<course.getLength(); i++) {
                String dept=((Element)((Element)course.item(i)).getElementsByTagName("dept").item(0)).getTextContent();
                int num=Integer.parseInt(((Element)((Element)course.item(i)).getElementsByTagName("num").item(0)).getTextContent());
                Grade g=new Grade(((Element)((Element)course.item(i)).getElementsByTagName("grade").item(0)).getTextContent());
                boolean transfer=Boolean.parseBoolean(((Element)((Element)course.item(i)).getElementsByTagName("transfer").item(0)).getTextContent());
                if(courses.containsKey(dept+" "+num)) {
                    CourseRecord r=courses.get(dept+" "+num);
                    r.addGrade(g);
                    if(!transfer && g.greaterThan(r.getCourse().getMinGrade())) {
                        transfer=false;
                    }
                } else {
                    Course c=School.getSchool().getDepartment(dept).findCourse(dept+" "+num);
                    CourseRecord r=new CourseRecord(c, g,transfer);
                    courses.put(c.getId(), r);
                }
            }

        } else {
            throw new Exception("Invalid File Exception");
        }
        
    }
    private void parseCourses(Element el, String dept) {
       NodeList courses= el.getElementsByTagName("course");
       for(int i=0; i<courses.getLength(); i++) {
           Element n=(Element)courses.item(i);
           String description=((Element)n.getElementsByTagName("number").item(0)).getTextContent();
           int number=Integer.parseInt(((Element)n.getElementsByTagName("number").item(0)).getTextContent());
           int offered=0;
           double mingrade=Double.parseDouble(((Element)n.getElementsByTagName("minGrade").item(0)).getTextContent());;
           int credits=Integer.parseInt(((Element)n.getElementsByTagName("credits").item(0)).getTextContent());
           Element offer=(Element) n.getElementsByTagName("offered").item(0);
           if(Boolean.parseBoolean(((Element)offer.getElementsByTagName("fall").item(0)).getTextContent())) {
               offered+=Course.FALL;
           }
           if(Boolean.parseBoolean(((Element)offer.getElementsByTagName("spring").item(0)).getTextContent())) {
               offered+=Course.SPRING;
           }
           Course c=new Course(dept,number,new Grade(mingrade), credits, offered);
           parsePrereqs(c, n,dept);
           c.setDescription(description);
           School s=School.getSchool();
           Department d=s.getDepartment(dept);
           if(d==null) {
               d=new Department(dept);
               s.addDepartment(d);
           }
           d.addCourse(c);
       }
    }
    private void parsePrereqs(Course c, Element e, String dept) {
        NodeList n=e.getElementsByTagName("prerequisite");
        for(int i=0; i<n.getLength(); i++) {
            NodeList n2=((Element)n.item(i)).getElementsByTagName("or");
            CourseGroup g=new CourseGroup();
            for(int j=0; j<n2.getLength(); j++) {
                String course=n2.item(j).getTextContent();
                g.addCourse(School.getSchool().getDepartment(dept).findCourse(course));
            }
            c.addPreReq(g);
        }
    }
    private Major parseMajorFile(Element el) {
        String name=el.getElementsByTagName("majorName").item(0).getTextContent();
        double gpa=Double.parseDouble(el.getElementsByTagName("minGPA").item(0).getTextContent());
        int minLocalCreds=Integer.parseInt(el.getElementsByTagName("minLocalCreds").item(0).getTextContent());

        Major m=new Major(name,gpa,minLocalCreds);
        /* each major is composed of requirements so load them*/
        NodeList nodes=el.getElementsByTagName("requirement");
        for(int i=0; i<nodes.getLength(); i++) {
            Requirement r=parseRequirement((Element) nodes.item(i));
            if(r!=null) {
                m.addRequirement(r);
            }
        }
        return m;
    }
    private Requirement parseRequirement(Element el) {

         String name=el.getElementsByTagName("name").item(0).getTextContent();
        int year=Integer.parseInt(el.getElementsByTagName("year").item(0).getTextContent());
        Requirement r=new Requirement();
        ArrayList<CourseGroup> list=new ArrayList<CourseGroup>();
        NodeList nodes=el.getElementsByTagName("sequence");

        String required=el.getAttribute("required");
        r.setYear(year);
        r.setId(name);
        int requiredSeq=nodes.getLength();
        if(!required.equals("")) {
            requiredSeq=Integer.parseInt(required);
        }
        /*
         * possible attributes
         *
         * minCredits-
         * minGPA-
         * minUpperDivision
         * minLocalCredits-
         */
        /*load course group for requirements*/
        int minCredits=0,minGPA=0, minLocalCredits=0;
        String s;
        if(!(s=el.getAttribute("minGPA")).equals("")) {
           r.setMinGPA(Double.parseDouble(s));
        }
        if(!(s=el.getAttribute("minCredits")).equals("")) {
            Integer.parseInt(s);
        }
        if(!(s=el.getAttribute("minLocalCredits")).equals("")) {
            r.setMinResidentCredits(Integer.parseInt(s));
        }
        if(!(s=el.getAttribute("minUpperDivision")).equals("")) {
            r.setMinUpperDivCredits(Integer.parseInt(s));
        }
        for(int i=0; i<nodes.getLength(); i++) {
            CourseGroup g=parseCourseGroup((Element)nodes.item(i));
            if(g!=null) {
                list.add(g);
            }
        }
        r.setPossibleCourses(list);
        return r;
    }
    private CourseGroup parseCourseGroup(Element el) {
        CourseGroup g=new CourseGroup();
        NodeList nodes=el.getElementsByTagName("course");
        for(int i=0; i<nodes.getLength(); i++) {
            String dept=nodes.item(i).getTextContent();
            Course c=School.getSchool().getDepartment(dept.split(" ")[0]).findCourse(dept);
            g.addCourse(c);
        }
        return g;
    }
    @Override
    public File writeFile(String cmd) {
        return null;//throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTextOfFile(String cmd) {
       String file="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
       return file;
    }
    private String writeCoursesFile(){return "";}
    private String writeRecordsFile(){return "";}
    private String writeMajorFile(){return "";}
 }

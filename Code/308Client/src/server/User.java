/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import persistence.PersistenceManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.*;
import java.util.Map.Entry;
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
    public int getStanding(){
        return School.getSchool().getStanding(this.getCompletedCredits());
    }
    public int getCompletedCredits() {
        int totalCreds=0;
        for(CourseRecord r: courses.values()) {
            if(r.coursePassed()) {
                totalCreds+=r.getCourse().getCredits();
            }
        }
        return totalCreds;
    }
    public double calculateGPA() {
        int totalCreds=0;
        double totalPoints=0;
        for(CourseRecord r: courses.values()) {
            for(Grade g : r.getGrades()) {
                totalPoints+=g.getGradePoints();
                totalCreds+=r.getCourse().getCredits();
            }
        }
        return totalPoints/totalCreds;
    }
    public TreeMap<String, CourseRecord> getRecords() {return courses;}
    public Major getMajor() {return major;}
    public long getID() {return userId;}
    public String getName() {return name;}
    public void setMajor(Major m) {major=m;}
    public int getMajorYear() {return majorYear;}
    public int getPermissions() {return permissions;}
    public TreeMap<String, CourseRecord> getCourses() {return courses;}

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
        return Schedule.generateSchedule(this);
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
                Department d2=School.getSchool().getDepartment(dept);
                d2.addMajor(major);
                PersistenceManager.merge(d2);
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
        School s=School.getSchool();
           Department d=s.getDepartment(dept);
           if(d==null) {
               d=new Department(dept);
               s.addDepartment(d);
           }
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
           //prereqs.put(c, this.parsePrereqs(n,dept));
           this.parsePrereqs(c, n, dept);
           c.setDescription(description);
           d.addCourse(c);
           PersistenceManager.merge(c);
       }
       /*for(Entry<Course, Vector<CourseGroup>> e : prereqs.entrySet()) {
           for(CourseGroup g : e.getValue()) {
               e.getKey().addPreReq(g);
               PersistenceManager.merge(g);
               PersistenceManager.merge(e.getKey());
           }
       }*/
       
       PersistenceManager.merge(d);
    }
    private void parsePrereqs(Course c, Element e, String dept) {
        NodeList n=e.getElementsByTagName("prerequisite");
        //Vector<CourseGroup> v=new Vector<CourseGroup>();
        for(int i=0; i<n.getLength(); i++) {
            NodeList n2=((Element)n.item(i)).getElementsByTagName("minstanding");
            if(n2.getLength()>0) {
                c.setMinLevel(Integer.parseInt(n2.item(0).getTextContent().substring(1)));
            }
            n2=((Element)n.item(i)).getElementsByTagName("or");
            CourseGroup g=new CourseGroup();
            for(int j=0; j<n2.getLength(); j++) {
                String course=n2.item(j).getTextContent();
                g.addCourse(School.getSchool().getDepartment(dept).findCourse(course));
            }
            //PersistenceManager.merge(g);
            c.addPreReq(g);
            //v.add(g);
        }
        //return v;
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
            PersistenceManager.merge(r);
            if(r!=null) {
                m.addRequirement(r);
            }
        }
        PersistenceManager.merge(m);
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
        String req=el.getAttribute("required");
        try{
            r.setNumberOfCourses(Integer.parseInt(req));
        }catch(Exception e){}
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
        FileWriter fw;
        PrintWriter pw;
        String fileName;

        try {
            if (cmd.equals("DOWNLOAD_COURSE_DATA"))
                fileName = "CoursesTaken.xml";
            else if(cmd.equals("DOWNLOAD_REQ"))
                fileName = "Major.xml";
            else
                fileName = null;

            fw = new FileWriter(fileName);
            pw = new PrintWriter(fw);

            if (fileName.equals("CoursesTaken.xml"))    {
                Collection<CourseRecord> courserecords = courses.values();
                int i = 0;
                pw.println("<file type=\"record\">");
                for(CourseRecord r : courserecords) {
                    pw.println("<course>");
                    pw.println("<dept>" + r.getCourse().getName() + "</dept>");
                    pw.println("<num>" + r.getCourse().getNum() + "</num>");
                    pw.println("<grade>" + r.getGrades().get(i) + "</grade>");
                    pw.println("<transfer" + r.getTransfer() + "</transfer>");
                    pw.println("</course>");
                    i++;
                }
                pw.println("</file>");
                pw.flush();
                fw.flush();
            }
            else if (fileName.equals("Major.xml"))   {
                School s = School.getSchool();
                Collection<Department> departments = s.getDepartments();
                pw.println("<file type=\"majorRequirement\">");
                
                for(Department d : departments) {
                    Collection<Major> majors = d.getMajors();

                    for(Major m : majors)   {
                        Collection<Requirement> requirements = m.getRequirements();
                        pw.println("<major>");
                        pw.println("<majorname>" + m.getId() + "</majorname>");

                        for(Requirement r : requirements)   {
                            pw.println("<minGPA>" + r.getMinGPA() + "</minGPA>");
                            pw.println("<minLocalCreds>" + r.getMinResidentCredits() + "</minLocalCreds>");
                            pw.println("<department>" + d.getName() + "</departments>");
                            pw.println("<requirement required=\"" + r.getNumberOfCourses() + "\">");
                            pw.println("<name>" + r.getId() + "</name>");
                            pw.println("<year>" + r.getYear() + "</year>");
                            Collection<CourseGroup> coursegroups = r.getPossibleCourses();

                            for(CourseGroup cg : coursegroups)  {
                                Collection<Course> courses = cg.getCourses();
                                pw.println("<sequence>");

                                for(Course c : courses) {
                                    pw.println("<course>" + c.getName() + "</course>");
                                }
                                pw.println("</sequence>");
                            }
                            pw.println("</requirement>");
                        }
                    }
                    pw.println("</major>");
                }
                pw.println("/file>");
                pw.flush();
                fw.flush();
            }

        }
        catch (IOException ioe)   {

        }
        return null;//throw new UnsupportedOperationException("Not supported yet.");
    }
/*
    @Override
    public String getTextOfFile(String cmd) {
       String file="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
       return file;
    }*/
    private String writeCoursesFile(){return "";}
    private String writeRecordsFile(){return "";}
    private String writeMajorFile(){return "";}

    @Override
    public String getTextOfFile(String cmd) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
 }
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
import java.io.Serializable;
import java.io.StringReader;
import java.util.Iterator;
import java.util.*;
import java.util.Map.Entry;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
/**
 *
 * @author tj
 */
public class User implements Scheduler, FileParser, Serializable{
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
        majorYear=(new Date()).getYear();
        courses=new TreeMap<String, CourseRecord>();
        userId=i;
        permissions=STUDENT;
        //majorYear=(new Date()).getYear();
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
        return Schedule.generateSchedule(this);
    }

    @Override
    public void parseFile(String f) throws Exception{
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        
        DocumentBuilder db= dbf.newDocumentBuilder();
        InputSource is=new InputSource();
        is.setCharacterStream(new StringReader(f));
        Document d=db.parse(is);
        parseXML(d);
    }
    private void parseXML(Document d) throws Exception{
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
            Vector<String> deptName=new Vector<String>();
            for(int i=0; i<depts.getLength(); i++) {
                String dept=((Element)((Element)depts.item(i)).getElementsByTagName("name").item(0)).getTextContent();
                deptName.add(dept);
                School s=School.getSchool();
               Department dep=s.getDepartment(dept);
               if(dep==null) {
                   dep=new Department(dept);
                   s.addDepartment(dep);
               }
            }
            int i=0;
            Vector<Course> v=new Vector<Course>();
            for(String dept : deptName) {
                 v.addAll(parseCourses((Element)depts.item(i), dept));
                 i++;
            }
            i=0;
            NodeList courseNodes=d.getElementsByTagName("course");
            for(Course c : v) {
                c.setPrereqs(parsePrereqs(c, (Element)courseNodes.item(i),c.getName()));
                i++;
                PersistenceManager.merge(c);
            }
        } else if(fileType.equals("record")) {
            String major=fileElement.getElementsByTagName("major").item(0).getTextContent();
            this.major=School.getSchool().findMajor(major);
            try{
            this.majorYear=Integer.parseInt(fileElement.getElementsByTagName("year").item(0).getTextContent());

            }catch(Exception e){}
            NodeList course=fileElement.getElementsByTagName("course");
            for(int i=0; i<course.getLength(); i++) {
                String dept=((Element)((Element)course.item(i)).getElementsByTagName("dept").item(0)).getTextContent();
                int num=Integer.parseInt(((Element)((Element)course.item(i)).getElementsByTagName("num").item(0)).getTextContent());
                Grade g=new Grade(((Element)((Element)course.item(i)).getElementsByTagName("grade").item(0)).getTextContent());
                boolean transfer=Boolean.parseBoolean(((Element)((Element)course.item(i)).getElementsByTagName("transfer").item(0)).getTextContent());
                if(courses.containsKey(dept+" "+num)) {
                    CourseRecord r=courses.get(dept+" "+num);
                    //r.addGrade(g);
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
    private Vector<Course> parseCourses(Element el, String dept) {
        School s=School.getSchool();
        Department d=s.getDepartment(dept);
           Vector<Course>courseVec=new Vector<Course>();
       NodeList courses= el.getElementsByTagName("course");
       for(int i=0; i<courses.getLength(); i++) {
           Element n=(Element)courses.item(i);
         
           String description=((Element)n.getElementsByTagName("number").item(0)).getTextContent();
           int number=Integer.parseInt(((Element)n.getElementsByTagName("number").item(0)).getTextContent());
           byte offered=0;
           double mingrade=Double.parseDouble(((Element)n.getElementsByTagName("minGrade").item(0)).getTextContent());;
           int credits=Integer.parseInt(((Element)n.getElementsByTagName("credits").item(0)).getTextContent());
           Element offer=(Element) n.getElementsByTagName("offered").item(0);
           if(Boolean.parseBoolean(((Element)offer.getElementsByTagName("fall").item(0)).getTextContent())) {
               offered|=OfferingList.FALL;
           }
           if(Boolean.parseBoolean(((Element)offer.getElementsByTagName("spring").item(0)).getTextContent())) {
               offered|=OfferingList.SPRI;
           }
           OfferingList ol = new OfferingList();
           ol.setNotListedStratagy(offered);
           Course c=new Course(dept,number,new Grade(mingrade), credits, ol);
           
           //this.parsePrereqs(c, n, dept);
           c.setDescription(description);
           d.addCourse(c);
           courseVec.add(c);
           //prereqs.put(c, this.parsePrereqs(c,n,dept));
           PersistenceManager.merge(c);
       }

       PersistenceManager.merge(d);
       return courseVec;
    }
    private ArrayList<CourseGroup> parsePrereqs(Course c, Element e, String dept) {
        NodeList n=e.getElementsByTagName("prerequisite");
        ArrayList<CourseGroup> v=new ArrayList<CourseGroup>();
        for(int i=0; i<n.getLength(); i++) {
            NodeList n2=((Element)n.item(i)).getElementsByTagName("minstanding");
            if(n2.getLength()>0) {
                c.setMinLevel(Integer.parseInt(n2.item(0).getTextContent().substring(1)));
            }
            n2=((Element)n.item(i)).getElementsByTagName("or");
            CourseGroup g=new CourseGroup();
            for(int j=0; j<n2.getLength(); j++) {
                String course=n2.item(j).getTextContent();
                g.addCourse(School.getSchool().getDepartment(course.split(" ")[0]).findCourse(course));
            }
            PersistenceManager.merge(g);
           // c.addPreReq(g);
            v.add(g);
        }
        return v;
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
        try{
         g.setNumReqiured(Integer.parseInt(el.getAttribute("required")));
        }catch(Exception e) {
            g.setNumReqiured(nodes.getLength());
        }
        for(int i=0; i<nodes.getLength(); i++) {
            String dept=nodes.item(i).getTextContent();
            Course c=School.getSchool().getDepartment(dept.split(" ")[0]).findCourse(dept);
            g.addCourse(c);
        }
        return g;
    }
   
    public String writeFile(String cmd, String department) {
        String s="";

        try {
  
            if (cmd.equals(Commands.DOWNLOAD_COURSE_DATA))    {
                Collection<CourseRecord> courserecords = courses.values();
                int i = 0;
                s+="<file type=\"record\">" + "\n";
                s+="\t<major>" + major + "</major>\n";
                s+="\t<year>" + majorYear + "</year>\n";
                for(CourseRecord r : courserecords) {
                    for(Grade g : r.getGrades()) {
                        Semester s2=r.getSemester(g);
                    s+="\t<course>" + "\n";
                    s+="\t\t<dept>" + r.getCourse().getName() + "</dept>" + "\n";
                    s+="\t\t<num>" + r.getCourse().getNum() + "</num>" + "\n";
                    s+="\t\t<grade>" + g.getGrade() + "</grade>" + "\n";
                    s+="\t\t<semester>";
                    s+="\t\t\t<year>"+s2.getYear()+"</year>";
                    s+="\t\t\t<season>"+s2.getSeason()+"</season>";
                    s+="\t\t</semester>";
                    s+="\t\t<transfer>" + r.getTransfer() + "</transfer>" + "\n";
                    s+="\t</course>" + "\n";
                    }
                }
                s+="</file>";
 
            }
            else if (cmd.equals(Commands.DOWNLOAD_REQ))   {
                School s2 = School.getSchool();
                Collection<Department> departments = s2.getDepartments();
                s+="<file type=\"majorRequirement\">\n";
                
                Collection<Major> majors = s2.getDepartment(department).getMajors();

                    for(Major m : majors)   {
                        Collection<Requirement> requirements = m.getRequirements();
                        s+="\t<major>\n";
                        s+="\t\t<majorname>" + m.getId() + "</majorname>\n";
             
                        for(Requirement r : requirements)   {
                            s+="\t\t<minGPA>" + r.getMinGPA() + "</minGPA>\n";
                            s+="\t\t<minLocalCreds>" + r.getMinResidentCredits() + "</minLocalCreds>\n";
                            s+="\t\t<department>" + department + "</department>\n";
                            s+="\t\t<requirement required=\"" + r.getNumberOfCourses() + "\">\n";
                            s+="\t\t\t<name>" + r.getId() + "</name>\n";
                            s+="\t\t\t<year>" + r.getYear() + "</year>\n";
                            Collection<CourseGroup> coursegroups = r.getPossibleCourses();

                            for(CourseGroup cg : coursegroups)  {
                                Collection<Course> courses = cg.getCourses();
                                s+="\t\t\t<sequence required=\"" + cg.getNumReqiured() + "\">\n";

                                for(Course c : courses) {
                                    s+="\t\t\t\t<course>" + c.getId() + "</course>\n";
                                }
                                s+="\t\t\t</sequence>\n";
                            }
                            s+="\t\t</requirement>\n";
                        }
                        s+="\t</major>\n";
                    }
                    s+="</file>";
             }
             else if (cmd.equals(Commands.DOWNLOAD_COURSES))   {
                School s2 = School.getSchool();
                Collection<Department> departments = s2.getDepartments();
                s+="<file type=\"courses\">\n";

                for (Department d : departments)    {
                    Collection<Course> courses = d.getCourses();
                    s+="\t<department>\n";
                    s+="\t\t<name>" + d.getName() + "</name>\n";

                    for (Course c : courses)    {
                        OfferingList semesters = c.getSemestersOffered();
                        Collection<CourseOffering> offerings = semesters.getOfferings();
                        s+="\t\t<course>\n";
                        s+="\t\t\t<number>" + c.getNum() + "</number>\n";
                        s+="\t\t\t<description>" + c.getDescription() + "</description>\n";
                        s+="\t\t\t<minGrade>" + c.getMinGrade().getGradePoints() + "</minGrade>\n";
                        s+="\t\t\t<credits>" + c.getCredits() + "</credits>\n";
                        s+="\t\t\t<offered>\n";

                        for (CourseOffering co : offerings) {
                            Semester sem = co.getSemester();
                            String semName = sem.toString();
                            if (semesters.isOffered(sem))
                                s+="\t\t\t\t<" + semName + ">" + semesters.isOffered(sem) + "</" + semName + ">\n";
                        }
                        s+="\t\t\t</offered>\n";
                        s+="\t\t</course>\n";
                    }
                    s+="\t</department>\n";
                }
                s+="</file>";
             }
        }
        catch (Exception ioe)   {

        }
        return s;//throw new UnsupportedOperationException("Not supported yet.");
    }
    @Override
    public void parseFile(File cmd) throws Exception {
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();

        DocumentBuilder db= dbf.newDocumentBuilder();

        Document d=db.parse(cmd);
        parseXML(d);
    }
 }
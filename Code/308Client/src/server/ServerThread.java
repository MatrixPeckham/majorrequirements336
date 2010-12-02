/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import persistence.PersistenceManager;
/**
 *
 * @author TJ
 */
public class ServerThread implements Runnable{

    private SystemManager system;
    private User user;
    private boolean connected;
    private Socket client;
    private Socket objects;
//    private BufferedReader rdr;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
  //  private PrintWriter pw;
    private InputStream in;
    private OutputStream out;
    private int permissions;
    ServerThread(Socket s) {
        client=s;
        permissions=User.STUDENT;
        connected=true;
        try{
            system=SystemManager.getSystemManager();
            user=system.addUser();
            //from socket streams, prepare various communication streams
            out=s.getOutputStream();
            in=s.getInputStream();
           
            //ObjectInput/OutputStreams- for passing objects between server and client
            objectOut=new ObjectOutputStream(out);
            objectIn=new ObjectInputStream(in);
             
        }catch(Exception e) {
            connected=false;
        }
    }
    private void clearStream() throws IOException {
        objectIn.skip(objectIn.available());
    }
    @Override
    /**
     * Protocol Definition-
     */
    public void run() {
        String cmd="";
        while(connected) {

            try{
                cmd=(String)objectIn.readObject();
               
                if(cmd.equals(Commands.LOGIN)) {
                    try {
                    String username=(String) objectIn.readObject();
                    String password=(String) objectIn.readObject();
                    String p=system.checkLogin(username,password);
                    if(Integer.parseInt(p)>=User.SUPER_ADMIN) {
                        permissions=User.SUPER_ADMIN;
                    } else if(p.equals(""+User.STUDENT)) {
                        permissions=User.STUDENT;
                    } else {
                        if(School.getSchool().getDepartment(p)==null) {
                            permissions=User.STUDENT;
                        } else {
                        permissions=User.DEPT_ADMIN;
                        }
                    }
                    objectOut.writeObject(p);
                    } catch(Exception e) {
                        objectOut.writeObject("0");
                    }
                } else if(cmd.equals(Commands.LOGOUT)) {
                    connected=false;
                    system.removeUser(user.getID());
                    objectOut.writeObject("OK");
                } else if(cmd.equals(Commands.ADD_MAJOR)) {
                    try{
                        Major m=(Major)objectIn.readObject();
                        School.getSchool().getDepartment((String) objectIn.readObject()).addMajor(m);
                        PersistenceManager.merge(m);
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_COURSE)) {
                    try{
                        Department d = School.getSchool().getDepartment((String) objectIn.readObject());
                        d.removeCourse((String) objectIn.readObject());
                        //PersistenceManager.merge(d);
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_MAJOR)) {
                     try{
                        Department d=School.getSchool().getDepartment((String) objectIn.readObject());
                        d.removeMajor((String) objectIn.readObject());
                        //PersistenceManager.merge(d);
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    } 
                } else if (cmd.equals(Commands.REMOVE_REQ)) {
                    try {
                        Major m = School.getSchool().getDepartment((String) objectIn.readObject()).findMajor((String) objectIn.readObject());
                        Collection<Requirement> r = m.getRequirements();
                        String s = (String) objectIn.readObject();
                        for(Requirement re : r) {
                            if (re.getId().equals(s))
                                m.removeRequirement(re);
                        }
                        objectOut.writeObject("OK");
                    }
                     catch(Exception e) {
    //                     pw.print("ERR");
                     }
                } else if (cmd.equals(Commands.DOWNLOAD_REQ)) {
                    try {
                        objectOut.writeObject(user.writeFile("DOWNLOAD_REQ"));
                        objectOut.writeObject("OK");
                    }
                    catch(Exception e)   {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_DEPT)) {
                    try {
                        School.getSchool().getDepartment((String) objectIn.readObject()).setName((String) objectIn.readObject());
                        objectOut.writeObject("OK");
                    }
                    catch(Exception e)  {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.GET_DEPT)) {
                     try{

                         objectOut.reset();
                         //objectOut=new ObjectOutputStream(out);
                         objectOut.writeObject(School.getSchool().getDepartments());
                         objectOut.writeObject("OK");
                         objectOut.flush();
                    }catch(Exception e) {
                        e.printStackTrace();
                        /*System.out.println("STRTERROR");
                        objectOut.writeObject(null);
                        objectOut.flush();
                        objectOut.writeObject("ERR");
                        System.out.println("ERROR");*/
                    }
                } else if (cmd.equals(Commands.GETDEPTCOURSES)) {
                    try{
                       
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).getCourses());
                        objectOut.writeObject("OK");
                          objectOut.flush();
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                         objectOut.flush();
                    }
                } else if (cmd.equals(Commands.GET_ALL_COURSES)) {
                    try{
                        
                        ArrayList<Department> depts = School.getSchool().getDepartments();
                        ArrayList<Course> courses = new ArrayList<Course>();
                        for(Department d : depts){
                            courses.addAll(d.getCourses());
                        }
                        objectOut.writeObject(courses);
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    } catch(Exception e){
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if (cmd.equals(Commands.GET_REQS)) {
                    try{

                        objectOut.reset();
                        objectOut.writeObject(user.getMajor().requirementsRemaining(user.getCourses(), user.getMajorYear()));
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if (cmd.equals(Commands.ADD_DEPT)) {
                    try{
                        System.out.println("LISTENDEPT");
                         
                        Department d=(Department)objectIn.readObject();
                        System.out.println(d.getName());
                        objectOut.writeObject("OK");
                         objectOut.flush();
                        System.out.println("PRINTED");
                        if(d!=null) {
                        School.getSchool().addDepartment(d);
                        System.out.println("ADDED");
                        }
                        
                    }catch(Exception e) {

                        e.printStackTrace();
                        objectOut.writeObject("ERR");
      //                  pw.flush();
                    } 
                } else if (cmd.equals(Commands.REMOVE_DEPT)) {
                    try{
                        School.getSchool().removeDepartment((String) objectIn.readObject());
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_CLASS)) {
                    try{
                        School.getSchool().getDepartment((String) objectIn.readObject()).removeCourse((String) objectIn.readObject());
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.UPLOADFILE)) {
                    try{
                        user.parseFile((String)objectIn.readObject());
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    } catch(Exception e) {
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if (cmd.equals(Commands.DOWNLOAD_COURSE_DATA)) {
                    try{
                        objectOut.writeObject(user.writeFile(Commands.DOWNLOAD_COURSE_DATA));
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.DOWNLOAD_SCHED)) {
                    try {

                        objectOut.writeObject("OK");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_COURSE_TAKEN)) {
                } else if (cmd.equals(Commands.EDIT_COURSE) || cmd.equals(Commands.ADD_CLASS)) {
                    try{
                        String s=(String) objectIn.readObject();
                        Course c=(Course)objectIn.readObject();
                        Department d=School.getSchool().getDepartment(s);
                        
                        d.addCourse(c);
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_MAJOR)) {
                  try   {
                      String s = (String)objectIn.readObject();
                      String mid = (String)objectIn.readObject();
                      Department d = School.getSchool().getDepartment(s);

                      ArrayList<Major> majors = d.getMajors();
                      for (Major m : majors)   {
                          if (m.getId().equals(mid))
                              m.setId((String) objectIn.readObject());
                      }
                      PersistenceManager.merge(d);
                      objectOut.writeObject("OK");
                  }
                  catch(Exception e)    {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                  }
                   
                } else if (cmd.equals(Commands.EDIT_REQUIREMENT)) {
                } else if (cmd.equals(Commands.GET_MAJOR)) {
                     try{
                         objectOut.reset();
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).findMajor((String) objectIn.readObject()));
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.GETSCHED)) {
                    try{
                        objectOut.reset();
                        objectOut.writeObject(user.generateSchedule());
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if (cmd.equals(Commands.GETCOURSE)) {
                    try{
                        objectOut.reset();
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).findCourse((String) objectIn.readObject()));
                        
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if (cmd.equals(Commands.ADD_COURSE_RECORD) || cmd.equals(Commands.EDIT_COURSE_RECORD)) {
                    try{
                       
                        CourseRecord r=(CourseRecord)objectIn.readObject();
                        user.getRecords().put(r.getCourse().getId(),r);
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }

                } else if(cmd.equals(Commands.REMOVE_COURSE_RECORD)){
                    try{
                        String str = (String)objectIn.readObject();
                        user.getRecords().remove(str);
                        objectOut.writeObject(true);
                        objectOut.flush();
                    } catch(Exception e){
                        e.printStackTrace();
                        objectOut.writeObject(false);
                        objectOut.flush();
                    }
                } else if(cmd.equals(Commands.GET_COURSE_RECORD)){
                    try{
                        String s = (String)objectIn.readObject();
                        objectOut.writeObject(user.getRecords().get(s));
                        out.flush();
                    } catch(Exception e){
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                }  else if (cmd.equals(Commands.ALL_MAJORS)) {
                    try{
                        objectOut.reset();
                        objectOut.writeObject(School.getSchool().getAllMajors());
                        objectOut.writeObject("OK");
                                objectOut.flush();
                    } catch(Exception e) {
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                }  else if(cmd.equals(Commands.GETUSER)) {
                    try{
                        objectOut.reset();
                        objectOut.writeObject(user);
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    } catch(Exception e) {
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else if(cmd.equals(Commands.CHANGEMAJOR)){
                    try{
                        user.setMajor((Major)objectIn.readObject());
                        objectOut.writeObject("OK");
                        objectOut.flush();
                    }catch(Exception e){
                        objectOut.writeObject("ERR");
                        objectOut.flush();
                    }
                } else {
                        //objectOut.writeObject(null);
                        //objectOut.writeObject("ERR");
                        throw new IllegalArgumentException("NO COMMAND:" + cmd);
                }

            } catch(SocketException se){
                connected=false;
            } catch (Exception e) {
        //        pw.println("ERR");
                e.printStackTrace();
            }
        }
    }
}

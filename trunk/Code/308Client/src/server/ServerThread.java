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
    private BufferedReader rdr;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;
    private PrintWriter pw;
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
                    permissions=system.checkLogin(username,password);
                    objectOut.writeObject(""+permissions);
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
                        School.getSchool().getDepartment((String) objectIn.readObject()).removeCourse((String) objectIn.readObject());
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_MAJOR)) {
                     try{
                        Department d=School.getSchool().getDepartment((String) objectIn.readObject());
                        d.removeMajor((String) objectIn.readObject());
                        PersistenceManager.merge(d);
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
                         pw.print("ERR");
                     }
                } else if (cmd.equals(Commands.DOWNLOAD_REQ)) {
                    try {
                        objectOut.writeObject(user.writeFile("DOWNLOAD_REQ"));
                        objectOut.writeObject("OK");
                    }
                    catch(Exception e)   {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.UPLOAD_REQ)) {
                    try {
                        user.parseFile(new File((String) objectIn.readObject()));
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

                        //objectOut.reset();
                         //objectOut=new ObjectOutputStream(out);
                         objectOut.writeObject(School.getSchool().getDepartments());
                         objectOut.flush();
                         objectOut.writeObject("OK");
                         pw.flush();
                         clearStream();
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
                        objectOut.reset();
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).getCourses());
                        objectOut.writeObject("OK");
                         pw.flush();
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                         pw.flush();
                    }
                } else if (cmd.equals(Commands.GET_ALL_COURSES)) {
                    try{
                        objectOut=new ObjectOutputStream(out);
                        ArrayList<Department> depts = School.getSchool().getDepartments();
                        ArrayList<Course> courses = new ArrayList<Course>();
                        for(Department d : depts){
                            courses.addAll(d.getCourses());
                        }
                        objectOut.writeObject(courses);
                        pw.println("OK");
                    } catch(Exception e){
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GET_REQS)) {
                    try{

                        objectOut.reset();
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).findMajor((String) objectIn.readObject()).getRequirements());
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
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
                        pw.flush();
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
                } else if (cmd.equals(Commands.UPLOAD_COURSE_DATA)) {

                } else if (cmd.equals(Commands.UPLOAD_SCHED)) {
                    try {
                        user.parseFile(new File((String) objectIn.readObject()));
                        objectOut.writeObject("OK");
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
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
                        Department d=School.getSchool().getDepartment((String) objectIn.readObject());
                        
                        d.addCourse((Course)objectIn.readObject());
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_MAJOR)) {
                   // user.getMajor().setId((String) objectIn.readObject());
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
                    }catch(Exception e) {
                        e.printStackTrace();
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.GETCOURSE)) {
                    try{
                        objectOut.reset();
                        objectOut.writeObject(School.getSchool().getDepartment((String) objectIn.readObject()).findCourse((String) objectIn.readObject()));
                        objectOut.flush();
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    }
                } else if (cmd.equals(Commands.ADD_COURSE_RECORD) || cmd.equals(Commands.EDIT_COURSE_RECORD)) {
                    try{
                       
                        CourseRecord r=(CourseRecord)objectIn.readObject();
                        user.getRecords().put(r.getCourse().getId(),r);
                        objectOut.writeObject("OK");
                    }catch(Exception e) {
                        objectOut.writeObject("ERR");
                    }

                } else {
                        //objectOut.writeObject(null);
                        //objectOut.writeObject("ERR");
                        throw new IllegalArgumentException("NO COMMAND:" + cmd);
                }

            } catch(SocketException se){
                connected=false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

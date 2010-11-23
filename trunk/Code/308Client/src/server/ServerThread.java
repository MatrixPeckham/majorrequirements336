/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.net.*;
import java.io.*;
import java.util.Collection;
/**
 *
 * @author TJ
 */
public class ServerThread implements Runnable{

    private SystemManager system;
    private User user;
    private boolean connected;
    private Socket client;
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
            
            //BufferedReader, for plain text reading
            rdr=new BufferedReader(new InputStreamReader(in));
            //PrintWriter- for plain text writing
            pw=new PrintWriter(out, true);
            //ObjectInput/OutputStreams- for passing objects between server and client
            //objectOut=new ObjectOutputStream(out);
            // objectIn=new ObjectInputStream(in);
             
        }catch(Exception e) {
            connected=false;
        }
    }

    @Override
    /**
     * Protocol Definition-
     */
    public void run() {
        while(connected) {
            try{
                String cmd=rdr.readLine();

                if(cmd.equals(Commands.LOGIN)) {
                    try {
                    String username=rdr.readLine();
                    String password=rdr.readLine();
                    permissions=system.checkLogin(username,password);
                    pw.println(permissions);
                    } catch(Exception e) {
                        pw.println("0");
                    }
                } else if(cmd.equals(Commands.LOGOUT)) {
                    connected=false;
                    system.removeUser(user.getID());
                    pw.println("OK");
                } else if(cmd.equals(Commands.ADD_MAJOR)) {
                    try{
                        School.getSchool().getDepartment(rdr.readLine()).addMajor((Major)objectIn.readObject());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if(cmd.equals(Commands.ADD_CLASS)) {
                    try{
                        School.getSchool().getDepartment(rdr.readLine()).addCourse((Course)objectIn.readObject());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_COURSE)) {
                    try{
                        School.getSchool().getDepartment(rdr.readLine()).removeCourse(rdr.readLine());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_MAJOR)) {
                     try{
                        School.getSchool().getDepartment(rdr.readLine()).removeMajor(rdr.readLine());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    } 
                } else if (cmd.equals(Commands.REMOVE_REQ)) {
                    try {
                        Major m = School.getSchool().getDepartment(rdr.readLine()).findMajor(rdr.readLine());
                        Collection<Requirement> r = m.getRequirements();
                        String s = rdr.readLine();
                        for(Requirement re : r) {
                            if (re.getId().equals(s))
                                m.removeRequirement(re);
                        }
                        pw.println("OK");
                    }
                     catch(Exception e) {
                         pw.print("ERR");
                     }
                } else if (cmd.equals(Commands.DOWNLOAD_REQ)) {
                    try {
                        user.writeFile("DOWNLOAD_REQ");
                        pw.println("OK");
                    }
                    catch(Exception e)   {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.UPLOAD_REQ)) {
                    try {
                        user.parseFile(new File(rdr.readLine()));
                        pw.println("OK");
                    }
                    catch(Exception e)   {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_DEPT)) {
                    try {
                        School.getSchool().getDepartment(rdr.readLine()).setName(rdr.readLine());
                        pw.println("OK");
                    }
                    catch(Exception e)  {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GET_DEPT)) {
                     try{
                         objectOut=new ObjectOutputStream(out);
                         objectOut.writeObject(School.getSchool().getDepartments());
                         //objectOut.close();
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GETDEPTCOURSES)) {
                    try{
                        objectOut.writeObject(School.getSchool().getDepartment(rdr.readLine()).getCourses());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GET_REQS)) {

                } else if (cmd.equals(Commands.ADD_DEPT)) {
                    try{
                        School.getSchool().addDepartment((Department)objectIn.readObject());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    } finally {
                    }
                } else if (cmd.equals(Commands.REMOVE_DEPT)) {
                    try{
                        //School.getSchool().removeDepartment(rdr.readLine());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.REMOVE_CLASS)) {
                    try{
                        School.getSchool().getDepartment(rdr.readLine()).removeCourse(rdr.readLine());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.UPLOAD_COURSE_DATA)) {

                } else if (cmd.equals(Commands.UPLOAD_SCHED)) {
                    try {
                        user.parseFile(new File(rdr.readLine()));
                        pw.println("OK");
                    }
                    catch (Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.DOWNLOAD_COURSE_DATA)) {
                    try{
                        pw.println(user.writeFile(Commands.DOWNLOAD_COURSE_DATA));
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.DOWNLOAD_SCHED)) {
                    try {

                        pw.println("OK");
                    }
                    catch (Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.EDIT_COURSE_TAKEN)) {
                } else if (cmd.equals(Commands.EDIT_COURSE)) {
                } else if (cmd.equals(Commands.EDIT_MAJOR)) {
                    user.getMajor().setId(rdr.readLine());
                } else if (cmd.equals(Commands.EDIT_REQUIREMENT)) {
                } else if (cmd.equals(Commands.GET_MAJOR)) {
                     try{
                        objectOut.writeObject(School.getSchool().getDepartment(rdr.readLine()).findMajor(rdr.readLine()));
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GETSCHED)) {
                    try{
                        objectOut.writeObject(user.generateSchedule());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.GETCOURSE)) {
                    try{
                        objectOut.writeObject(School.getSchool().getDepartment(rdr.readLine()).findCourse(rdr.readLine()));
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    }
                } else if (cmd.equals(Commands.ADD_COURSE_RECORD)) {

                } else if (cmd.equals(Commands.EDIT_COURSE_RECORD)) {
                } else {
                        objectOut.writeObject(null);
                        pw.println("ERR");
                        throw new IllegalArgumentException("NO COMMAND:" + cmd);
                }

            } catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                try {
                   pw.flush();
                   objectOut.flush();
                   objectOut.reset();
                }
                catch(Exception e)  {
                    pw.println("ERR");
                }
                finally {
                    pw.flush();
                }
            }
        }
    }
}

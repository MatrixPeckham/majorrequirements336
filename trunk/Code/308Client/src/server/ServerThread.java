/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.net.*;
import java.io.*;
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
            objectOut=new ObjectOutputStream(out);
             objectIn=new ObjectInputStream(in);
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
                    pw.flush();
                    } catch(Exception e) {
                        pw.println("0");
                        pw.flush();
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

                } else if (cmd.equals(Commands.REMOVE_REQ)) {
                } else if (cmd.equals(Commands.DOWNLOAD_REQ)) {
                } else if (cmd.equals(Commands.UPLOAD_REQ)) {
                } else if (cmd.equals(Commands.EDIT_DEPT)) {
                } else if (cmd.equals(Commands.GET_DEPT)) {
                     try{
                        objectOut.writeObject(School.getSchool().getDepartments());
                        objectOut.flush();
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    } finally {
                        objectOut.flush();
                        pw.flush();
                    }
                } else if (cmd.equals(Commands.GETDEPTCOURSES)) {
                      try{
                        objectOut.writeObject(School.getSchool().getDepartment(rdr.readLine()).getCourses());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
                    } finally {
                        objectOut.flush();
                        pw.flush();
                    }
                } else if (cmd.equals(Commands.GET_REQS)) {
                } else if (cmd.equals(Commands.ADD_DEPT)) {
                    try{
                        School.getSchool().addDepartment((Department)objectIn.readObject());
                        pw.println("OK");
                    }catch(Exception e) {
                        pw.println("ERR");
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
                } else if (cmd.equals(Commands.DOWNLOAD_COURSE_DATA)) {
                } else if (cmd.equals(Commands.DOWNLOAD_SCHED)) {
                } else if (cmd.equals(Commands.EDIT_COURSE_TAKEN)) {
                } else if (cmd.equals(Commands.EDIT_COURSE)) {
                } else if (cmd.equals(Commands.EDIT_MAJOR)) {
                } else if (cmd.equals(Commands.EDIT_REQUIREMENT)) {
                } else if (cmd.equals(Commands.GET_MAJOR)) {
                } else if (cmd.equals(Commands.GETSCHED)) {
                } else if (cmd.equals(Commands.GETCOURSE)) {
                } else if (cmd.equals(Commands.ADD_COURSE_RECORD)) {
                } else if (cmd.equals(Commands.EDIT_COURSE_RECORD)) {
                }


            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}

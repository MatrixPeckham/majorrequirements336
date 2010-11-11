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
            pw=new PrintWriter(out);
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
                    String username=rdr.readLine();
                    String password=rdr.readLine();
                    permissions=system.checkLogin(username,password);
                } else if(cmd.equals(Commands.LOGOUT)) {
                    connected=false;
                    system.removeUser(user.getID());

                } else if(cmd.equals(Commands.ADD_CLASS)) {

                } else if(cmd.equals(Commands.ADD_DEPT)) {
                    
                } else if(cmd.equals(Commands.ADD_MAJOR)) {

                } else if(cmd.equals(Commands.DOWNLOAD_COURSE_DATA)) {

                } else if(cmd.equals(Commands.DOWNLOAD_REQ)) {

                } else if(cmd.equals(Commands.DOWNLOAD_SCHED)) {

                } else if(cmd.equals(Commands.EDIT_COURSE)) {

                } else if(cmd.equals(Commands.EDIT_COURSE_TAKEN)) {

                } else if(cmd.equals(Commands.EDIT_MAJOR)) {

                } else if(cmd.equals(Commands.EDIT_REQUIREMENT)) {

                } else if(cmd.equals(Commands.GET_MAJOR)) {

                } else if(cmd.equals(Commands.GETSCHED)) {

                } else if(cmd.equals(Commands.REMOVE_CLASS)) {

                } else if(cmd.equals(Commands.REMOVE_DEPT)) {

                } else if(cmd.equals(Commands.REMOVE_MAJOR)) {

                } else if(cmd.equals(Commands.REMOVE_REQ)) {

                } else if(cmd.equals(Commands.UPLOAD_COURSE_DATA)) {

                } else if(cmd.equals(Commands.UPLOAD_REQ)) {

                } else if(cmd.equals(Commands.UPLOAD_SCHED)) {
                    File f=(File) objectIn.readObject();
                }


            } catch(Exception e) {

            }
        }
    }
}

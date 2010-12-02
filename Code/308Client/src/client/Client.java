package client;

import java.io.File;
import java.io.IOException;
import java.net.Socket;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.*;
import java.io.*;
import java.net.SocketException;
import java.util.Collection;
import java.util.Scanner;
import javax.swing.JOptionPane;
import server.Commands;
/**
 * Main networking class for the client.
 * @author Bill
 *
 */
//TODO finish this class all methods marked with TODO need
//TODO server classes the rest are stubs that need socket interaction
public class Client{

	/**Socket for communication with server*/
	private Socket s;
//TODO public Schedule generateSchedule() {return null;}
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
//        private BufferedReader rdr;
  //      private PrintWriter pw;
//TODO public Schedule generateSchedule() {return null;}
	
	public File getFile(File location, String str) {
            try{
            FileWriter fw=new FileWriter(location);
            PrintWriter pw2=new PrintWriter(fw);
            oos.writeObject(str);
            String s;
            while(!(s=(String) ois.readObject()).equals("ENDXML")) {
                oos.writeObject(s);
            }
//            pw.flush();
                return location;
            }catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
                return null;
            }

        }
	public int uploadFile(File file, String str) {
            try{
            oos.writeObject(str);
            
            Scanner sc=new Scanner(file);
            String s="";
            while(sc.hasNext()) {
                s+=sc.nextLine();
            }
            oos.writeObject(s);
            String s2=(String) ois.readObject();
            return s2.equals("OK")?0:Integer.parseInt(s2);
            }catch(SocketException se){
            connectionLostGTFO();
            return -1;
        } catch(Exception e) {
            return -1;
            }
        }
//TODO	public boolean addCourse(Course c, String str) {return false;}
//TODO	public Course loadCourse(String str) {return null;}
	public boolean addCourse(Course c, String str) {
            try{
                oos.writeObject(Commands.ADD_CLASS);
                oos.writeObject(str);
                oos.writeObject(c);
                    oos.flush();
                    ois.readObject();
                return true;
            }catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {e.printStackTrace();return false;}
        }
    	public Course loadCourse(String str) {
            try{
                oos.writeObject(Commands.GETCOURSE);
                oos.writeObject(str);
                //ois=new ObjectInputStream(s.getInputStream());
                return (Course) ois.readObject();
            }catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){return null;}
        }
	public boolean removeCourse(String d, String str) {
            try{
                oos.writeObject(Commands.REMOVE_COURSE);
                oos.writeObject(d);
                oos.writeObject(str);
                return ((String) ois.readObject()).equals("OK");
            } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e){
                return false;
            }
        }
//TODO	public boolean editCourse(Course c) {return false;}
//TODO	public Major loadMajor(String str) {return null;}
//TODO	public void addMajor(Major m) {}
//TODO	public boolean editMajor(Major m) {return false;}




	public boolean removeMajor(String d, String str) {
            try {
                oos.writeObject(Commands.REMOVE_COURSE);
                oos.writeObject(d);
                oos.writeObject(str);
                return ((String) ois.readObject()).equals("OK");
            } catch(SocketException se){
            connectionLostGTFO();
            return false;
        }
            catch(Exception e)   {
                return false;
            }
        }
//TODO	public boolean addRequirement(Requirement r, String str) {return false;}

	public boolean removeRequirement(String str1, String str2){return false;}
	public int login(String usr,String pass) {
            try{
            oos.writeObject(Commands.LOGIN);
            oos.writeObject(usr);
            oos.writeObject(pass);
            oos.flush();
            String resp=(String) ois.readObject();
                    int p=User.DEPT_ADMIN;
            try {
                p=Integer.parseInt(resp);
            } catch(Exception e) {}
            if(p==User.STUDENT) {return User.STUDENT;}
            else if(p>=User.SUPER_ADMIN) {return User.SUPER_ADMIN;}
            else {
                            this.currDepo=resp;
                            return User.DEPT_ADMIN;
            }
            } catch(SocketException se){
            connectionLostGTFO();
            return -1;
        } catch(Exception e) {
                return -1;
            }
        }
	public boolean logout() {
            try{
            oos.writeObject(Commands.LOGOUT);
  
            return ((String) ois.readObject()).equals("OK");
            } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {return false;}
        }
//TODO	public Vector<Requirements> getRequirements() {return null;}

	public int getCreditsRemaining() {return 0;}
//TODO	public void addDepartment(String, Department) {}

	public boolean removeDepartment (String str) {
            try{
                oos.writeObject(Commands.REMOVE_DEPT);
                oos.writeObject(str);
                ois.readObject();
                return true;
            } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
                return false;
            }
        }
//TODO	public void editDepartment(String str, Department d) {}
//TODO	public Vector<Department> getDepartments() {return null;}
//TODO	public Department getDepartment(String str) {return null;}
//TODO	public Vector<Course> getDepartmentCourses(String) {return null;} 
//TODO	public void editDepartment(String str, Department d) {}
//TODO	public Vector<Department> getDepartments() {return null;}
//TODO	public Department getDepartment(String str) {return null;}
//TODO	public Vector<Course> getDepartmentCourses(String) {return null;}

        Client() {
            try {
                //first thing initiate connection
                s = new Socket("localhost", 8989);
              
//                rdr=new BufferedReader(new InputStreamReader(s.getInputStream()));
  //              pw=new PrintWriter(s.getOutputStream(), true);
                ois=new ObjectInputStream(s.getInputStream());
                oos=new ObjectOutputStream(s.getOutputStream());

                
            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception se){
                JOptionPane.showMessageDialog(null, "There was a problem connecting to the server the program cannot continue");
                System.exit(-1);
            }
        }

    Schedule generateSchedule() {
        try{
            oos.writeObject(Commands.GETSCHED);
            oos.flush();
            Object s= ois.readObject();

            //ois=new ObjectInputStream(s.getInputStream());
            ois.readObject();
            return (Schedule) s;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){
            return null;
        }
    }

    boolean editCourse(Course c) {
        try{
        oos.writeObject(Commands.EDIT_COURSE);

            oos.reset();
        oos.writeObject(c);
        
        return ((String) ois.readObject()).equals("OK");
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
            return false;
        }
    }

    Major loadMajor(String str) {
        try{
        oos.writeObject(Commands.GET_MAJOR);
        oos.writeObject(str);
                   //ois=new ObjectInputStream(s.getInputStream());
            return (Major)ois.readObject();
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    boolean addMajor(Major m, String dept) {
        try{
        oos.writeObject(Commands.ADD_MAJOR);
        oos.writeObject(m);
            oos.reset();
            oos.writeObject(dept);
            return ((String) ois.readObject()).equals("OK");
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
            return false;
        }
    }

    boolean editMajor(Major m, String dept, String s) {
        try{
        oos.writeObject(Commands.EDIT_MAJOR);
        oos.writeObject(dept);
            oos.reset();
            oos.writeObject(m.getId());
            oos.reset();
            oos.writeObject(s);

            return ((String) ois.readObject()).equals("OK");
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
            return false;
        }
    }

    boolean addRequirement(Requirement r, String dept, String major) {

        return false;
    }
    ArrayList<Requirement> getRequirements(String dept, String major) {
       try{
           oos.writeObject(Commands.GET_REQS);
           oos.writeObject(dept);
           oos.writeObject(major);
           oos.flush();
           MajorCompletion m=(MajorCompletion)ois.readObject();
           ois.readObject();
           return new ArrayList<Requirement>();
       } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
           return null;
       }
    }

    boolean addDepartment(Department dep) {
        boolean ret = false;
    //    pw.flush();
        System.out.println("printed");
        try{
        oos.writeObject(Commands.ADD_DEPT);
             
             //oos=new ObjectOutputStream(s.getOutputStream());
             
             oos.writeObject(dep);
             oos.flush();
             String s=(String) ois.readObject();
             System.out.println(s);
            System.out.println("result read: " +s);
            return s.equals("OK");
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    boolean editDepartment(String str, Department d) {
        try{
        oos.writeObject(Commands.EDIT_DEPT);
        oos.writeObject(str);
            

            oos.writeObject(d);

            return ((String) ois.readObject()).equals("OK");
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e) {
            return false;
        }
    }

    ArrayList<Department> getDepartments() {
        try{
        oos.writeObject(Commands.GET_DEPT);
        oos.flush();
           // ois=new ObjectInputStream(s.getInputStream());
           //System.out.println((String) ois.readObject());
            ArrayList<Department> a=(ArrayList<Department>)ois.readObject();
            System.out.println((String) ois.readObject());
            return a;

        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    Department getDepartment(String str) {
        ArrayList<Department> a=getDepartments();
        Department ret = null;
        if(a==null) {return null;}
        for(Department d : a) {
            if(d.getName().equals(str)) {
                ret = d;
                break;
            }
        }
        return ret;
    }

    ArrayList<Course> getDepartmentCourses(String str) {
        try{
       oos.writeObject(Commands.GETDEPTCOURSES);
       oos.writeObject(str);
       oos.flush();
        //ois=new ObjectInputStream(s.getInputStream());
        ArrayList<Course> arr = new ArrayList((Collection<Course>)ois.readObject());
        System.out.println(ois.readObject());
        return arr;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
            return null;
        }
    }
public User getStudentInfo() {
        try{
            oos.writeObject(Commands.GETUSER);
            oos.flush();
            //ois = new ObjectInputStream(s.getInputStream());
            Object o = ois.readObject();
            ois.readObject();
            //oos.reset();
            return (User)o;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
}

    String getCurrentDepartment() {
        
        return currDepo;
    }

    CourseRecord getCourseRecord(String str) {
        try{
            oos.writeObject(Commands.GET_COURSE_RECORD);
            oos.writeObject(str);
            Object o = ois.readObject();
            if(o instanceof CourseRecord)
                return (CourseRecord)o;
            else
                return null;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    boolean removeCourseRecord(String str) {
        try{
            oos.writeObject(Commands.REMOVE_COURSE_RECORD);
            oos.writeObject(str);
            return (Boolean)ois.readObject();
        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }

    String checkSchedule() {
       try{
        oos.writeObject(Commands.GET_REQS);
           oos.flush();
           MajorCompletion m=(MajorCompletion)ois.readObject();
           Object o=ois.readObject();
           return m.getHtml();
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {return  null;}

    }

    int addCourseRecord(CourseRecord r) {
        try{
            oos.writeObject(Commands.ADD_COURSE_RECORD);
            return sendCourseRecord(r);
        } catch(SocketException se){
            connectionLostGTFO();
            return -1;
        } catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    int editCourseRecord(CourseRecord r) {
        try{

        oos.writeObject(Commands.EDIT_COURSE_RECORD);
        return sendCourseRecord(r);
        } catch(SocketException se){
            connectionLostGTFO();
            return -1;
        } catch(Exception e){return -1;}
    }
    private int sendCourseRecord(CourseRecord r) {
        try{
            oos.writeObject(r);
            return Integer.parseInt((String) ois.readObject());
        } catch(SocketException se){
            connectionLostGTFO();
            return -1;
        } catch(Exception e) {
            return -1;
        }
    }
    Major getMajor(String dept, String major) {
        try{
        oos.writeObject(Commands.GET_MAJOR);
        oos.writeObject(dept);
        oos.writeObject(major);
            //ois=new ObjectInputStream(s.getInputStream());
            Major m = (Major) ois.readObject();
            ois.readObject();
            return m;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    Major getCurrentMajor() {

        return getStudentInfo().getMajor();
    }

    public Course getCourse(String string) {
        try{
        oos.writeObject(Commands.GETCOURSE);
        oos.writeObject(string.substring(0, 3));
        oos.writeObject(string);
        oos.flush();
            //ois=new ObjectInputStream(s.getInputStream());
            Course c = (Course) ois.readObject();
            ois.readObject();
            return c;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e) {
            return null;
        }
    }

    public void downloadFile(File file, String str) {
        getFile(file,str);
       /* try {
            oos.writeObject(Commands.DOWNLOAD_COURSE_DATA);
            oos.flush();
        }
        catch(Exception e)  {
            e.printStackTrace();
        }*/
    }
    public ArrayList<Major> getAllMajors() {
         try{
            oos.writeObject(Commands.ALL_MAJORS);
            oos.flush();
            Object o = ois.readObject();
            ArrayList<Major> c = new ArrayList<Major>((Collection<Major>)o);
            ois.readObject();
            oos.reset();
            return c;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    ArrayList<Course> getAllCourses() {

        try{
            oos.writeObject(Commands.GET_ALL_COURSES);
            oos.flush();
            Object o = ois.readObject();
            ArrayList<Course> c = new ArrayList<Course>((Collection<Course>)o);
            ois.readObject();
            oos.reset();
            return c;
        } catch(SocketException se){
            connectionLostGTFO();
            return null;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    boolean changeMajor(Major selectedItem) {
        try{
            oos.writeObject(Commands.CHANGEMAJOR);
            oos.writeObject(selectedItem);
            oos.flush();
            return ((String) ois.readObject()).equals("OK");

        } catch(SocketException se){
            connectionLostGTFO();
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    void setCurrentDepartment(String dep) {
        currDepo=dep;
    }
    String currDepo="CSE";

    void connectionLostGTFO(){
        JOptionPane.showMessageDialog(null, "The connection to the server has been lost.\nUnable to continue, the program will exit.");
        System.exit(-1);
    }
}

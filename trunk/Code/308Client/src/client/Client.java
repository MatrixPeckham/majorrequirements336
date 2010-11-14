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
        private BufferedReader rdr;
        private PrintWriter pw;
//TODO public Schedule generateSchedule() {return null;}
	public File getFile(String str) {return null;}
	public File getFile(String str, File location) {
            try {
                pw.println(str);
                int fileSize=Integer.parseInt(rdr.readLine());
                FileOutputStream fs=new FileOutputStream(location);
                byte[] b=new byte[fileSize];
                s.getInputStream().read(b);
                fs.write(b);
                return location;
            } catch(Exception e) {return null;}

        }
	public int uploadFile(File file, String str) {return 0;}
//TODO	public boolean addCourse(Course c, String str) {return false;}
//TODO	public Course loadCourse(String str) {return null;}
	public boolean addCourse(Course c, String str) {
            try{
                pw.println(Commands.ADD_CLASS);
                oos.writeObject(c);
                return Boolean.parseBoolean(rdr.readLine());
            }catch(Exception e) {return false;}
        }
    	public Course loadCourse(String str) {
            try{
                pw.println(Commands.GETCOURSE);
                pw.println(str);
                return (Course) ois.readObject();
            }catch(Exception e){return null;}
        }
	public boolean removeCourse(String str) {
            try{
                //pw.println(Commands.REMOVE_COURSE);
                return false;
            } catch(Exception e){
                return false;
            }
        }
//TODO	public boolean editCourse(Course c) {return false;}
//TODO	public Major loadMajor(String str) {return null;}
//TODO	public void addMajor(Major m) {}
//TODO	public boolean editMajor(Major m) {return false;}




	public boolean removeMajor(String str) {return false;}
//TODO	public boolean addRequirement(Requirement r, String str) {return false;}

	public boolean removeRequirement(String str1, String str2){return false;}
	public int login(String usr,String pass) {return 3;}
	public boolean logout() {return false;}
//TODO	public Vector<Requirements> getRequirements() {return null;}

	public int getCreditsRemaining() {return 0;}
//TODO	public void addDepartment(String, Department) {}

	public void removeDepartment (String str) {}
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

            } catch (UnknownHostException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
/*
    //TODO TOM FILL THIS
    public User getStudentInfo() {
        return null;
    }

    public Schedule generateSchedule() {
        return null;
    }

    boolean editCourse(Course c) {
        return false;
    }

    Major loadMajor(String str) {
        return null;
    }

    void addMajor(Major m) {
        //auto stub
    }

    boolean editMajor(Major m) {
        return false;
    }

    boolean addRequirement(Requirement r, String str) {
        return false;
    }

    ArrayList<Requirement> getRequirements() {
        return null;
    }

    void addDepartment(String str, Department dep) {
        //does nothing
    }

    void editDepartment(String str, Department d) {
        //does nothing
    }

    ArrayList<Department> getDepartments() {
        return null;
    }

    Department getDepartment(String str) {
        return null;
    }

    ArrayList<Course> getDepartmentCourses(String str) {
        return null;
    }

    String getCurrentDepartment() {
        return "";
    }

    CourseRecord getCourseRecord(String str) {
        return null;
    }

    boolean removeCourseRecord(String str) {
        return true;
    }

    void downloadFile(File file, String str) {
        //do nothing
    }

    ArrayList<Requirement> checkSchedule() {
        return null;
    }

    void addCourseRecord(CourseRecord r) {
        //do nothing
    }

    void editCourseRecord(CourseRecord r) {
        //do nothing
    }

    Major getMajor(String string) {
        return null;
    }

    String getCurrentMajor() {
        return null;
    }

    Course getCourse(String string) {
        return null;
    }

    ////////////////////////////////////////////////////
    //////////////STILL MAY NEED MORE///////////////////
    ////////////////////////////////////////////////////
 * 
 */
}
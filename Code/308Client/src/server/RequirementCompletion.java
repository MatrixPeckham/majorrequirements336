/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.io.*;
import java.util.Vector;
/**
 *
 * @author TJ
 */
public class RequirementCompletion implements Serializable{
    /*public Vector<CourseCompletion> courseMessages;
    public boolean complete;
    public int localCreds;
    public int credsEarned;
    public int credsTaken;
    public double gradePoints;
    public String name;
    public String message;
    RequirementCompletion(){courseMessages=new Vector<CourseCompletion>();}*/
    public Vector<SequenceCompletion> seq;
    public boolean complete;
    public int localCreds;
    public int credsEarned;
    public int credsTaken;
    public double gradePoints;
    public String name;
    public String message;
    RequirementCompletion(){seq=new Vector<SequenceCompletion>();}
    /*public String getHtml() {
        String s="<h3>"+name+"</h3><table>";
        s+="<tr>";
        s+="<th>Course</th><th>Completed</th><th>Status</th>";
        s+="</tr>";
        for(CourseCompletion c : courseMessages) {
            s+="<tr><td>";
            s+=c.course.getId()+"</td>";
            s+="<td>"+c.complete+"</td>";
            s+="<td>"+c.message+"</td></tr>";
        }
        s+="</table>";
        s+=message;
        return s;
    }*/
    public String getHtml() {
        String s="<h1>"+name+"</h1>";
        for(SequenceCompletion sc : seq) {
            s+=sc.getHtml();
            s+="<hr/>";
        }
        s+="<h2>" + message + "</h2>";
        return s;
    }
}

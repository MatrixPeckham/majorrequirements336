
package server;
import java.io.*;
import java.util.Vector;

/**
 *
 * @author Chris
 */
public class SequenceCompletion implements Serializable{
    public Vector<CourseCompletion> courseMessages;
    public boolean complete;
    public int classesNeeded;
    SequenceCompletion(){courseMessages=new Vector<CourseCompletion>(); }
        public String getHtml() {
        String s="Classes Required: " + classesNeeded + "<table>";
        s+="<tr>";
        s+="<th>Course</th><th>Completed</th><th>Status</th>";
        s+="</tr><tr>";
        for(CourseCompletion c : courseMessages) {
            s+="<td>";
            s+=c.course.getId()+"</td>";
            s+="<td>"+c.complete+"</td>";
            s+="<td>"+c.message+"</td></tr>";
        }
        s+="</table>";
        return s;
    }
}

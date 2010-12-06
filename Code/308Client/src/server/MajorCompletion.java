/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author TJ
 */
public class MajorCompletion implements Serializable{
    public Vector<RequirementCompletion> reqs;
    public boolean complete;
    public String message;
    public String name;
    MajorCompletion() {reqs=new Vector<RequirementCompletion>();}
    public String getHtml() {
        String s="<html><body align=\"center\">";
        s+="<h1>"+name+"</h1>";
        for(RequirementCompletion r: reqs) {
            s+=r.getHtml()+"<br>";
            s+="<hr/><hr/>";
        }
        s+="<h1>" + message + "</h1>";
        s+="</body></html>";
        return s;
    }
}

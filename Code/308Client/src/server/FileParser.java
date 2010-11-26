/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server;
import java.io.*;
/**
 *
 * @author TJ
 */
public interface FileParser {

    public void parseFile(File f) throws Exception;
    public File writeFile(String cmd);
    public void parseFile(String file) throws Exception;
}

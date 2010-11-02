package logging;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.Date;

public class LoggerClass {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup() throws IOException {
		// Create Logger
		Logger logger = Logger.getLogger("");
		logger.setLevel(Level.INFO);
                Date d=new Date();
		fileTxt = new FileHandler("Logger"+d.getMonth()+"-"+d.getDay()+"-"+(d.getYear()+1900)+"-"+d.getTime()+".txt");

		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);
	}
}

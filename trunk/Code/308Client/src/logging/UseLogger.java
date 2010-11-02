package logging;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UseLogger {
	// Always use the classname, this way you can refactor
       
	private final static Logger LOGGER = Logger.getLogger(UseLogger.class
			.getName());
       public static void severe(String msg) {
           setup();
           LOGGER.setLevel(Level.SEVERE);
           LOGGER.severe(msg);
       }
       public static void warning(String msg) {
           setup();
           LOGGER.setLevel(Level.WARNING);
           LOGGER.warning(msg);
       }
       public static void info(String msg) {
          setup();
           LOGGER.setLevel(Level.INFO);
           LOGGER.info(msg);
       }

	public void writeLog() {
		// Set the LogLevel to Severe, only severe Messages will be written
		LOGGER.setLevel(Level.SEVERE);
		LOGGER.severe("Info Log");
		LOGGER.warning("Info Log");
		LOGGER.info("Info Log");
		LOGGER.finest("Really not important");

		// Set the LogLevel to Info, severe, warning and info will be written
		// Finest is still not written
		LOGGER.setLevel(Level.INFO);
		LOGGER.severe("Info Log");
		LOGGER.warning("Info Log");
		LOGGER.info("Info Log");
		LOGGER.finest("Really not important");
	}

	public static void setup() {
		try {
			LoggerClass.setup();
                        
		} catch (IOException e) {
			e.printStackTrace();
                        
			throw new RuntimeException("Problems with creating the log files");
		}
	}
}


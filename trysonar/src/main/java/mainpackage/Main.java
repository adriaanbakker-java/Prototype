package mainpackage;

import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {


    public static void main(String[] args) {
        Logger loggerX =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Main.class);

        int a = 30;
        // dit is commentaar
        // in geval van PC met windows moet je de volgende regel uitcommentarieren
        // a = 10
        loggerX.info("Example log from {} .... a={}", Main.class.getSimpleName(),  a);

        ch.qos.logback.classic.Logger parentLogger =
                (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("com.baeldung.logback");
        parentLogger.setLevel(Level.INFO);

        Logger childlogger =
                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("com.baeldung.logback.tests");

        parentLogger.warn("This message is logged because WARN > INFO.");
        parentLogger.debug("This message is not logged because DEBUG < INFO.");
        childlogger.info("INFO == INFO");
        childlogger.debug("DEBUG < INFO");

        ch.qos.logback.classic.Logger logger =
                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger("com.baeldung.logback");
        logger.info("Hi there!");
        logger.info("Current count is {}", a);



    }
}
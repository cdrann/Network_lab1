package ru.nsu.fit.cdrann;

import java.io.*;
import java.util.logging.*;

public class Main {
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LogManager logManager = LogManager.getLogManager();
        try {
            InputStream loggingProperties = new FileInputStream("log.properties");
            logManager.readConfiguration(loggingProperties);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Cannot get log configuration");
        }

        if (args.length == 1) {
            CopyFinder finder = new CopyFinder(args[0]);

            if (AddressValidator.isValidRange(args[0])) {
                logger.log(Level.INFO, "Beginning of CopyFinder work...");

                finder.start();
            } else {
                logger.log(Level.SEVERE, "Not a valid range address");
            }
        } else {
            logger.log(Level.SEVERE, "Invalid arg format.");
        }
    }

}

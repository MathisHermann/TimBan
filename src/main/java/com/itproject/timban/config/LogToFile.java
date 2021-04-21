package com.itproject.timban.config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Author: Mathis
 * Peer: -
 * Reviewer: -
 * Date: 19.04.2021
 */

public class LogToFile {

    protected static final Logger logger = Logger.getLogger("MyLog");


    /**
     * Log an entry to the log file. Is only concerning non-user stuff. Login / Logout and Checkin / Checkin is handled in another file.
     *
     * This method is derived from information on https://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
     * Called on 19.04.2021
     * @param level
     * @param msg
     */
    public static void logSystem(String level, String msg) {
        FileHandler fh = null;

        try {
            fh = new FileHandler("system_logs.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if (msg.length() > 0) {
                switch (level) {
                    case "info":
                        logger.log(Level.INFO, msg);
                        break;
                    case "warning":
                        logger.log(Level.WARNING, msg);
                        break;
                    case "fine":
                        logger.log(Level.FINE, msg);
                        break;
                    case "finer":
                        logger.log(Level.FINER, msg);
                        break;
                    case "finest":
                        logger.log(Level.FINEST, msg);
                        break;
                    default:
                        logger.log(Level.CONFIG, msg);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fh != null)
                fh.close();
        }

    }

    public static void logUser(String msg) {
        FileHandler fh = null;

        try {
            fh = new FileHandler("user_logs.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            logger.info(msg);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fh != null)
                fh.close();
        }
    }


}

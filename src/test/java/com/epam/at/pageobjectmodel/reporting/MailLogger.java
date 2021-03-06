package com.epam.at.pageobjectmodel.reporting;

import org.apache.log4j.Logger;

public class MailLogger {

    public static Logger logger = Logger.getLogger(MailLogger.class);

    public static void fatal(String message) {
        logger.fatal(message);
    }

    public static void fatal(String message, Throwable throwable) {
        logger.fatal(message, throwable);
    }

    public static void error(String message) {
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void warn(String message) {
        logger.warn(message);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}

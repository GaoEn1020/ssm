package com.htht.util;

import org.apache.log4j.Logger;

public class AppLogService {
	  // Constants
    private static final String LOGGER_EVENTS = "hdht.event";
    private static final String LOGGER_DEBUG = "hdht.debug";
    private static final String LOGGER_ERRORS = "hdht.error";

    private static Logger _loggerEvents;
    private static Logger _loggerErrors;
    private static Logger _loggerDebug;

    /**
     * Creates a new AppLogService object.
     */
    private AppLogService(  )
    {
    }

    /**
     * initializes the errors log file and the application log file
     * @param strConfigPath The strConfigPath
     * @param strConfigFile The strConfigFile
     */
	public static void init( )
    {
        //Initialize the logger and configures it with the values of the properties file : config.properties
        try
        {
            // Get a logger for errors
            _loggerErrors = Logger.getLogger( LOGGER_ERRORS );
            // Get a logger for application events
            _loggerEvents = Logger.getLogger( LOGGER_EVENTS);
//            _loggerEvents.setAdditivity( false );

            // Get a logger for debug and trace
            _loggerDebug = Logger.getLogger( LOGGER_DEBUG );
//            _loggerDebug.setAdditivity( false );
        }
        catch ( Exception e )
        {
            System.err.println( "Bad Configuration of Log4j : " + e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Log4j wrappers

    /**
     * Tells if the logger accepts debug messages. If not it prevents to build
     * consuming messages that will be ignored.
     * @return True if the logger accepts debug messages, otherwise false.
     */
    public static boolean isDebugEnabled()
    {
        return _loggerDebug.isDebugEnabled(  );
    }
    /**
     * Log a message object with the DEBUG level. It is logged in application.log
     *
     * @param objToLog the message object to log
     */
    public static void debug( Object objToLog )
    {
        if ( _loggerDebug.isDebugEnabled(  ) )
        {
            _loggerDebug.debug( objToLog );
        }
    }

    /**
     * Tells if the logger accepts debug messages. If not it prevents to build
     * consuming messages that will be ignored.
     * @return True if the logger accepts debug messages, otherwise false.
     */
    public static boolean isDebugEnabled( String strLogger )
    {
        Logger logger = Logger.getLogger( strLogger );
        return logger.isDebugEnabled(  );
    }
    /**
     * Log a message object with the DEBUG level. It is logged in application.log
     *
     * @param objToLog the message object to log
     */
    public static void debug( String strLogger , Object objToLog )
    {
        Logger logger = Logger.getLogger( strLogger );
        if ( logger.isDebugEnabled(  ) )
        {
            logger.debug( objToLog );
        }
    }

    /**
     * Log a message object with the ERROR Level. It is logged in error.log
     *
     * @param objToLog the message object to log
     */
    public static void error( Object objToLog )
    {
        _loggerErrors.error( objToLog );
    }

    /**
     * Log a message object with the ERROR level including the stack trace of	
     * the Throwable t passed as parameter. It
     * is logged in error.log
     *
     * @param message the message object to log
     * @param t the exception to log, including its stack trace
     */
    public static void error( Object message, Throwable t )
    {
        _loggerErrors.error( message, t );
    }

    /**
     * Log a message object with the INFO Level in application.log
     *
     * @param objToLog the message object to log
     */
    public static void info( Object objToLog )
    {
        if ( _loggerEvents.isInfoEnabled(  ) )
        {
            _loggerEvents.info( objToLog );
        }
    }

}

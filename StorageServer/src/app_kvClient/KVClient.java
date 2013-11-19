
package app_kvClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import client.KVCommInterface;
import client.KVStore;
import common.messages.KVMessage;


public class KVClient{


    private KVCommInterface connection; // reference to connection interface

    public KVClient () {
    }

    /**
     * The main method that starts the application and interacts with the user
     * within the defined protocol.
     */
    public void startApplication () {
	Logger logger = LoggingManager.getInstance ().createLogger ( this.getClass () );

	// initialize buffer reader to read user input.
	BufferedReader cons = new BufferedReader ( new InputStreamReader (
		System.in ) );
	logger.debug ( "Input Stream Reader created" );
	// the flag to stop shell interaction
	boolean quit = false;
	while ( ! quit ) {
	    System.out.print ( UserFacingMessages.ECHO_PROMPT );
	    String input;
	    String [] tokens;
	    try {
		input = cons.readLine ();
		tokens = input.trim ().split ( UserFacingMessages.SPLIT_ON );
		// user input was split as tokens.
		// safety check
		if ( tokens == null || tokens.length == 0 ) {
		    throw new IllegalArgumentException (
			    UserFacingMessages.GENERAL_ILLIGAL_ARGUMENT );
		}

		// start parsing the tokens
		KVCommand command = KVCommand
			.fromString ( tokens [ 0 ] );
		ValidationUtil validationUtil = ValidationUtil.getInstance ();
		switch ( command ) {
		case CONNECT :
		    if ( validationUtil.isValidConnectionParams ( tokens ) ) {
			if (connection == null){
			    connection = new KVStore(tokens [ 1 ] , Integer.parseInt(tokens[2]));
			    }
			connection.connect ();
			logger.info ("Connected to KV server, "+tokens[1]+":"+tokens[2]);
		    }
		    break;
		case DISCONNECT :
		    connection.disconnect ();
		    logger.info ( "Connection closed." );
		    break;
		case PUT :
		    if ( validationUtil.isValidStoreParams ( tokens ) ) {
			KVMessage result = connection.put(tokens[1], tokens[2]);
			logger.info(handleResponse(result));
//			logger.info ( "Put request sent to KV server." );
		    }
		    break;

		case GET :
		    if (tokens[1] != null && !tokens[1].isEmpty()){
			KVMessage result = connection.get(tokens[1]);
			logger.info(handleResponse(result));

		    }else{
			logger.warn("Key was not provided.");
		    }
		    break;
		case LOG_LEVEL :
		    if ( validationUtil.isValidLogLevel ( tokens ) ) {
			LoggingManager.getInstance ().setLoggerLevel ( tokens [ 1 ] );
			logger.info ( "Log Level Set to: " + tokens [ 1 ] );
		    }
		    break;
		case HELP :
		    System.out.println ( UserFacingMessages.HELP_TEXT );
		    logger.info ( "Help Text provided to user." );
		    break;

		case UN_SUPPORTED :
		    System.out
		    .println ( UserFacingMessages.UN_SUPPORTED_COMMAND );
		    logger.warn ( "User entered unsupported command." );
		    break;

		case QUIT :
		    quit = true;
		    connection.disconnect ();
		    logger.info ( "Quit program based on user request." );
		    break;

		default :
		    break;
		}

	    } catch ( Exception e ) {
		// report issue to user
		logger.error ( e.getMessage () );

	    }

	}

    }

    private String handleResponse(KVMessage result) {
	String resultText ="";
	switch (result.getStatus()) {
	case GET_ERROR:
	    resultText = UserFacingMessages.GET_ERROR_MESSAGE+ result.getValue();
	    break;
	case GET_SUCCESS:
	    resultText = UserFacingMessages.GET_SUCCESS_MESSAGE+ result.getValue();
	    break;

	case PUT_ERROR:
	    resultText = UserFacingMessages.PUT_ERROR_MESSAGE+ result.getValue();
	    break;   

	case PUT_SUCCESS:
	    resultText = UserFacingMessages.GET_SUCCESS_MESSAGE;
	    break;

	case PUT_UPDATE :
	    resultText = UserFacingMessages.PUT_UPDATE_MESSAGE;
	    break;   

	case DELETE_SUCCESS:
	    resultText = UserFacingMessages.DELETE_SUCCESS_MESSAGE;
	    break;
	case DELETE_ERROR:
	    resultText = UserFacingMessages.DELETE_ERROR_MESSAGE+ result.getValue();
	    break;
	default:
	    resultText= result.getStatus()+result.getKey()+result.getValue();
	}

	return resultText;
    }



}

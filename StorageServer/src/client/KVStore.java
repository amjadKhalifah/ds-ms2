package client;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Observable;

import org.apache.log4j.Logger;

import common.messages.KVMessage;
import common.messages.Message;

public class KVStore implements KVCommInterface {

	private Logger logger = Logger.getRootLogger();		
	private String address;
	private int port;
	
	private Socket clientSocket;
	private OutputStream output;
 	private InputStream input;
 	
 	private static final int BUFFER_SIZE = 1024;
	private static final int DROP_SIZE = 1024 * BUFFER_SIZE;
		
	/**
	 * Initialize KVStore with address and port of KVServer
	 * @param address the address of the KVServer
	 * @param port the port of the KVServer
	 */
	public KVStore(String address, int port) {
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void connect() throws Exception {
		// TODO Auto-generated method stub		
		try {
			clientSocket = new Socket(address, port);								
			output = clientSocket.getOutputStream();
			input = clientSocket.getInputStream();					
			logger.info("Connection established with server");			
		} catch (IOException ioe) {
			logger.error("Connection could not be established!");			
		} 
	}

	@Override
	public void disconnect() {			
		// notify the observers that the connection closed
		try {
			tearDownConnection();			
		} catch (IOException ioe) {
			logger.error("Unable to close connection!");
		}
	}
	
	private void tearDownConnection() throws IOException {		
		logger.info("tearing down the connection ...");
		if (clientSocket != null) {
			input.close();
			output.close();
			clientSocket.close();
			clientSocket = null;
			logger.info("connection closed!");
		}
	}
		

	@Override
	public KVMessage put(String key, String value) throws Exception {
		// TODO Auto-generated method stub
		Message msg= new Message();
		KVMessage receivedMsg = null;
		msg.setKey(key);
		msg.setValue(value);
		msg.setStatus(KVMessage.StatusType.PUT);
		try{
			this.sendMessage(msg);
			receivedMsg = this.receiveMessage();
			
		}catch ( IOException e){
			logger.error("");
		}		
		return receivedMsg;
	}

	@Override
	public KVMessage get(String key) throws Exception {		
		Message msg= new Message();
		KVMessage receivedMsg = null;
		msg.setKey(key);		
		msg.setStatus(KVMessage.StatusType.GET);
		try{
			this.sendMessage(msg);
			receivedMsg = this.receiveMessage();
			
		}catch ( IOException e){
			logger.error("");
		}		
		return receivedMsg;
	}
	
	
	
	private KVMessage receiveMessage() throws IOException {		//	
	    byte [] data = null;
	    try {	    	
	    	data = new byte [ clientSocket.getReceiveBufferSize () ];
	    	input.read ( data );
	    	System.out.println( data.length );
	    }catch ( IOException e){
	    	e.printStackTrace();
	    }

		
		/* build final String */
		KVMessage msg = SerializationUtil.toObject(data);
		logger.info("Receive message:\t '" + msg.getKey() + "'");
		return msg;
    }
	
	
private void sendMessage(KVMessage msg) throws IOException {
	byte[] msgBytes = SerializationUtil.toByteArray(msg);
	System.out.println("amjad"+msgBytes.length);
	output.write(msgBytes, 0, msgBytes.length);
	output.flush();
	logger.info("Send message vvvv:\t '" + msg.getKey() + "'");
}
	
}

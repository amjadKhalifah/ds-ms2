package app_kvClient;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Level;

import logger.LogSetup;

public class TestClient {
	
	public static void main (String agrs[]){
		try {
			new LogSetup("logs/client.log", Level.ALL);
			KVClient client = new KVClient("localhost", 5000);			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

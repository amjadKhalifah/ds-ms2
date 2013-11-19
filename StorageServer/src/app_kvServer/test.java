package app_kvServer;

import java.io.IOException;

import logger.LogSetup;

import org.apache.log4j.Level;

public class test {
	public static void main(String args[]){
		try {
			new LogSetup("logs/server.log", Level.ALL);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		KVServer server = new KVServer(5000);
		server.startServer();
	}
}

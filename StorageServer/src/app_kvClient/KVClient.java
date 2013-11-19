
package app_kvClient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.apache.log4j.Logger;

import client.KVStore;

import common.messages.KVMessage;


public class KVClient{
		
	
	public KVClient(String address, int port) 
			throws UnknownHostException, IOException {		
		try {
			KVStore store = new KVStore(address, port);			
			store.connect();			
			KVMessage msg = store.put("Ibrahim", "Alzant");
			KVMessage r = store.get("Ibrahim");
			KVMessage d = store.put("Ibrahim", "Khalifa");
			KVMessage a = store.get("Ibrahim");
			System.out.println(msg.getKey());
			System.out.println("w;slhw;lhg;wlerkgh;wlerhgw;lerhgl;wekg"+a.getKey());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

}

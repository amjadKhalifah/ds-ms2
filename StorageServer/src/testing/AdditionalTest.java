package testing;

import org.junit.Test;

import client.SerializationUtil;
import common.messages.KVMessage;
import common.messages.KVMessage.StatusType;
import common.messages.Message;
import junit.framework.TestCase;

public class AdditionalTest extends TestCase {
	
	
	@Test
	public void testStub() {
		assertTrue(true);
	}
	
	@Test
	public void testPutSerialization(){
	    
	    Message putMessage = new Message();
	    putMessage.setKey("Key1");
	    putMessage.setValue("Value1");
	    putMessage.setStatus(StatusType.PUT);
	    
	    assertNotNull("Message is null",putMessage);
	    
	    byte[] byteStream = SerializationUtil.toByteArray(putMessage);
	    
	    assertNotNull("Message serialization failed."+byteStream);
	    
	    KVMessage deserializedMessage = SerializationUtil.toObject(byteStream);
	    
	    assertNotNull("Message deserialization failed.",deserializedMessage);
	    
	    assertTrue(putMessage.getKey().equals(deserializedMessage.getKey()));
	    assertTrue(putMessage.getStatus().equals(deserializedMessage.getStatus()));
	    assertTrue(putMessage.getValue().equals(deserializedMessage.getValue()));
	    
	}
	
	@Test
	public void testGetSerialization(){
	    
	    Message getMessage = new Message();
	    getMessage.setKey("Key1");
	    getMessage.setValue("Value1");
	    getMessage.setStatus(StatusType.GET);
	    
	    assertNotNull("Message is null",getMessage);
	    
	    byte[] byteStream = SerializationUtil.toByteArray(getMessage);
	    
	    assertNotNull("Message serialization failed."+byteStream);
	    
	    KVMessage deserializedMessage = SerializationUtil.toObject(byteStream);
	    
	    assertNotNull("Message deserialization failed.",deserializedMessage);
	    
	    assertTrue(getMessage.getKey().equals(deserializedMessage.getKey()));
	    assertTrue(getMessage.getStatus().equals(deserializedMessage.getStatus()));
	    assertTrue(getMessage.getValue().equals(deserializedMessage.getValue()));
	    
	}
	@Test
	public void testGetErrorSerialization(){
	    
	    Message getMessage = new Message();
	    getMessage.setKey("Key1");
	    getMessage.setValue("Value1");
	    getMessage.setStatus(StatusType.GET_ERROR);
	    
	    assertNotNull("Message is null",getMessage);
	    
	    byte[] byteStream = SerializationUtil.toByteArray(getMessage);
	    
	    assertNotNull("Message serialization failed."+byteStream);
	    
	    KVMessage deserializedMessage = SerializationUtil.toObject(byteStream);
	    
	    assertNotNull("Message deserialization failed.",deserializedMessage);

	    assertTrue(getMessage.getKey().equals(deserializedMessage.getKey()));
	    assertTrue(getMessage.getStatus().equals(deserializedMessage.getStatus()));
	    assertTrue(getMessage.getValue().equals(deserializedMessage.getValue()));
	}
	
	@Test
	public void testDeleteSerialization(){
	    
	    Message deleteMessage = new Message();
	    deleteMessage.setKey("Key1");
	    deleteMessage.setValue("Value1");
	    deleteMessage.setStatus(StatusType.DELETE_SUCCESS);
	    
	    assertNotNull("Message is null",deleteMessage);
	    
	    byte[] byteStream = SerializationUtil.toByteArray(deleteMessage);
	    
	    assertNotNull("Message serialization failed."+byteStream);
	    
	    KVMessage deserializedMessage = SerializationUtil.toObject(byteStream);
	    
	    assertNotNull("Message deserialization failed.",deserializedMessage);
	    
	    
	    assertTrue(deleteMessage.getValue().equals(deserializedMessage.getValue()));
	    assertTrue(deleteMessage.getKey().equals(deserializedMessage.getKey()));
	    assertTrue(deleteMessage.getStatus().equals(deserializedMessage.getStatus()));

	    
	}
	
	
}

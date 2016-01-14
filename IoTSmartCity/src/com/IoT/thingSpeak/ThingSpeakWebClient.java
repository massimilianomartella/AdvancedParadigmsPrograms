package com.IoT.thingSpeak;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ThingSpeakWebClient {
	private String apiWriteKey = "Q7PHB8FHLU89SBBC";
	private int channelNumber = 73899;
	private Channel channel;
	Entry entry;
	
	public ThingSpeakWebClient() {
		
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
		channel = new Channel(channelNumber, apiWriteKey);
		entry = new Entry();
	}
	
	public void sendMsg(int field1, Double value1, int field2, Double value2) {
		
		Entry entry = new Entry();
		entry.setField(field1, String.valueOf(value1));
		entry.setField(field2, String.valueOf(value2));
		try {
			channel.update(entry);
			// System.out.println("Msg sended!:"+value1);
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ThingSpeakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		ThingSpeakWebClient ts = new ThingSpeakWebClient();
		// Test send Msg
		ts.sendMsg(1, 28.9, 2, 34.3);
	}
}

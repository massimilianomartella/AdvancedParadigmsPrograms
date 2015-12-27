package com.IoT.thingSpeak;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ThingSpeakWebClient {
	String apiWriteKey = "Q7PHB8FHLU89SBBC";
	int channelNumber = 73899;

	public ThingSpeakWebClient() {
		// TODO Auto-generated constructor stub
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
	}

	public synchronized void sendMsg(int field1, Double value) {

		Channel channel = new Channel(channelNumber, apiWriteKey);
		
		

		Entry entry = new Entry();
		entry.setField(field1, String.valueOf(value));
		try {
			channel.update(entry);
			System.out.println("Msg sended!");
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
		ts.sendMsg(1, 28.9);
	}
}

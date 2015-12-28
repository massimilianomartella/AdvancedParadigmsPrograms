package com.IoT.thingSpeak;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.angryelectron.thingspeak.Channel;
import com.angryelectron.thingspeak.Entry;
import com.angryelectron.thingspeak.ThingSpeakException;
import com.angryelectron.thingspeak.log4j.ThingSpeakAppender;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ThingSpeakWebClient {
	private String apiWriteKey = "Q7PHB8FHLU89SBBC";
	private int channelNumber = 73899;
	private Channel channel;

	public ThingSpeakWebClient() {

		System.setProperty("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.Jdk14Logger");
		channel = new Channel(channelNumber, apiWriteKey);
	}

	public synchronized void sendMsgOld(int field1, Double value) {

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
	
	public synchronized void sendMsg(int field1, Double value) {
		
		Entry entry = new Entry();
		entry.setField(field1, String.valueOf(value));
		ThingSpeakAppender appender = new ThingSpeakAppender(entry);
		appender.configureChannel(channelNumber, apiWriteKey, "http://api.thingspeak.com");
		appender.setThreshold(Level.INFO);
		appender.activateOptions();
		appender.close();
		Logger.getRootLogger().addAppender(appender);
		Logger.getLogger(this.getClass()).log(Level.INFO, "Hello World");
	}

	public static void main(String[] args) throws Exception {

		ThingSpeakWebClient ts = new ThingSpeakWebClient();
		//ts.sendMsg(1, 28.9);
		ts.sendMsg(1, 28.9);
	}
}

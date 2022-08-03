package com.revature.P0.util;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author trenton
 * class that takes in any events that I want to record  and write it to some file
 */
public class Logger {
	private static Logger logger = new Logger();
	private Logger() {
		
	}
	
	public static Logger getLogger(){
		return logger;
	}
	private void writeToFile(String message) {
		try (FileWriter writer = new FileWriter("app.log", true)){
			writer.append(message+"\n");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void log(LogLevel logLevel, String message) {
		Log newLog = new Log(logLevel, LocalDateTime.now(), message);
		writeToFile(newLog.toString());
	}
	
	private class Log{
		LogLevel logLevel;
		LocalDateTime timestamp;
		String message;
		
		public Log(LogLevel logLevel, LocalDateTime timestamp, String message) {
			super();
			this.logLevel = logLevel;
			this.timestamp = timestamp;
			this.message = message;
		}
		@Override
		public String toString() {
			return logLevel + "\t" + timestamp + "\t" + message;
		}
	}
	public  enum LogLevel {
		info, debug, verbose, warning, fatal, error
	}
}


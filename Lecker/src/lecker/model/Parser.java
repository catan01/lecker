package lecker.model;

import java.util.Calendar;

public class Parser {
	private Thread thread = null;
	private static Parser instance = null;
	
	
	
	private Parser() {
		thread = new Thread(new ParserThread());
		thread.start();
	}
	
	public static void init() {
		if (instance == null) {
			instance = new Parser();
		}
	}
	
	
	
	private void parse() {
		//TODO
	}
	
	private void firstParse() {
		//TODO
	}
	
	
	
	
	
	private class ParserThread implements Runnable {
		@Override
		public void run() {
			//start
			firstParse();
			
			while (true) {
				try {
					// new date
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
					calendar.set(Calendar.HOUR, 13);
					calendar.set(Calendar.MINUTE,37);
					wait(calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
					parse();
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
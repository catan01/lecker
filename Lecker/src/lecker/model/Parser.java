package lecker.model;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Parser {
	private Timer parserTimer = null;
	private static Parser instance = null;
	
	private static final long PARSE_PERIOD = 86400000; //24 hours = 86 400 000 ms
	private static final int DAYS_TO_PARSE = 14;
	
	private static Date lastParsed = null;
	
	private Parser() {
		parserTimer = new Timer();
		parserTimer.scheduleAtFixedRate(new ParserTask(), new Date(), PARSE_PERIOD);
	}
	
	public static void init() {
		if (instance == null) {
			instance = new Parser();
		}
	}
	
	private class ParserTask extends TimerTask {
		@Override
		public void run() {
			
			ParserOldenburg parser = new ParserOldenburg();
			
			for(int i = 0; i <= DAYS_TO_PARSE; i++) {
				parser.parse(i);
			}
			
			lastParsed = new Date();
		}
	}
	
	public static Date getLastParsed() {
		return lastParsed;
	}
}
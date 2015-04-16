package com.ubs.opsit.interviews;

import java.util.StringTokenizer;

/**
 * Convert the time passed as a string in hh:mm:ss and output as a Berlin Clock output.
 * For input values and expected output see test file berlin-clock.story 
 *  
 * @author Bernhard Roth
 *
 */
public class TimeConverter {

	// Output creation constants
	private static final String[] SECONDS_DISPLAY   = {"Y", "O"};

	private static final String FIVE_HOUR_DISPLAY   = "RRRROOOO";
	private static final String ONE_HOUR_DISPLAY    = "RRRROOOO";
	
	private static final String FIVE_MINUTE_FILLER  = "OOOOOOOOOOO";
	private static final String FIVE_MINUTE_DISPLAY = "YYRYYRYYRYY";
	private static final String ONE_MINUTE_DISPLAY  = "YYYYOOOO";

	/**
	 * Converts the time string to a valid clock output or a error message.
	 * This is not throwing an exception to allow for 
	 * 
	 * @param aTime the time passed as a string in hh:mm:ss 
	 * @return a valid clock output or a error message
	 */
	public String convertTime(String aTime) {
		StringBuilder output = new StringBuilder(34);

		try {

			// Parse the time according to the specific needs of the Berlin clock
			TimeParser parser = new TimeParser(aTime);
			
			// Seconds output choosing even values from index zero and odd values from index 1 
			output.append(SECONDS_DISPLAY[parser.getSeconds() % 2]).append(System.lineSeparator());
			
			/* Five hours output. This is a sliding window over the predefined constant.
			 * e.g. hour=17 -> R[RRRO]OOO  or  hour=2 -> RRRR[OOOO]
			 * The start index is at 4 - (hour / 5) length is always 4 
			 */
			int fiveHourBeginIndex = 4 - (parser.getHours() / 5);
			output.append(FIVE_HOUR_DISPLAY.substring(fiveHourBeginIndex, fiveHourBeginIndex + 4))
				.append(System.lineSeparator());
			
			// One hours output. This is a sliding window over the predefined constant. 
			// The start index is at 4 - (hour % 5) length is always 4
			int oneHourBeginIndex = 4 - (parser.getHours() % 5);
			output.append(ONE_HOUR_DISPLAY.substring(oneHourBeginIndex, oneHourBeginIndex + 4))
				.append(System.lineSeparator());
			
			// Five minutes output. This is the start sequence of the constant, padded with the filler to 11 characters.
			// e.g. minutes=43 / 5 = 8 -> [YYRYYRYY] + OOO
			int fiveMinuteEndIndex = parser.getMinutes() / 5;
			output.append(FIVE_MINUTE_DISPLAY.substring(0, fiveMinuteEndIndex))
				.append(FIVE_MINUTE_FILLER.substring(fiveMinuteEndIndex, 11))
				.append(System.lineSeparator());
			
			// One minute output. This is a sliding window over the predefined constant, same as in One hours output.
			int oneMinuteBeginIndex = 4 - (parser.getMinutes() % 5);
			output.append(ONE_MINUTE_DISPLAY.substring(oneMinuteBeginIndex, oneMinuteBeginIndex + 4));

		} catch (IllegalArgumentException iae) {
			return iae.getMessage();
		}
		
		return output.toString();
    }


	/**
	 * Parse a time format into hour, minute, second values
	 * 
	 * According to the test procedure a time of 24:00:00 is valid and should be displayed.
	 * This prevents usage of SimpleDateFormatter to parse the time so parsing is done manually.
	 * 
	 * The assumption is that the range lies between 00:00:00 and 24:59:59
	 * A time of 24:59:58 switches on all lights for testing.
	 * 
	 * @author Bernhard Roth
	 */
	private class TimeParser {

		// Time parsing constants
		private static final String TIME_DELIMITER = ":"; 

		private int hours;
		private int minutes;
		private int seconds;

		/**
		 * Instantiate the class and parse the values
		 * @param aTime A time in the format as specified in {parse(String aTime)}
		 * @throws IllegalArgumentException
		 */
		private TimeParser(String aTime) throws IllegalArgumentException {
			parse(aTime);
		}
		
		/**
		 * Expect a string in the format hh:mm:ss and tokenize it into the member variables
		 *    
		 * @param aTime A time as hh:mm:ss between 00:00:00 and 24:59:59
		 * @throws IllegalArgumentException if the format is incorrect or the values are out of bounds. 
		 */
		private void parse(String aTime) throws IllegalArgumentException {

			// Setup the tokenizer with delimiter ':'
			StringTokenizer tokenizer = new StringTokenizer(aTime, TIME_DELIMITER);
			
			// We expect exactly three tokens
			if (tokenizer.countTokens() != 3) {
				throw new IllegalArgumentException("ERROR: Invalid time format. Expected: hh:mm:ss - Received: "+aTime);
			}

			// Parse the tokens into the array of integers
			try {
				hours   = Integer.parseInt(tokenizer.nextToken());
				minutes = Integer.parseInt(tokenizer.nextToken());
				seconds = Integer.parseInt(tokenizer.nextToken());
			} catch (NumberFormatException iae) {
				throw new IllegalArgumentException("ERROR: Invalid time value. Expected: between 00:00:00 and 24:59:59 - Received: '"+aTime+"'", iae);
			}

			// Verify that the tokens are within bounds
			if (hours < 0 || hours > 24	|| minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
				throw new IllegalArgumentException("ERROR: Time out of bounds. Expected between 00:00:00 and 24:59:59 - Received: "+aTime);
			}
		}
		
		/**
		 * @return the hours
		 */
		public int getHours() {
			return hours;
		}

		/**
		 * @return the minutes
		 */
		public int getMinutes() {
			return minutes;
		}

		/**
		 * @return the seconds
		 */
		public int getSeconds() {
			return seconds;
		}
	}
}

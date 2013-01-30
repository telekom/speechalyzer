package com.felix.util;

public class DateTimeUtil {
	/**
	 * Return a new timesString,in form "hh:mm", but maximally "23:59"
	 * 
	 * @param time
	 * @param hours
	 * @return
	 */
	public static String addHoursToTime(String time, int hours) {
		String[] parts = time.split(":");
		String hourS = parts[0];
		String minS = parts[1];
		int newHours = Integer.parseInt(hourS) + hours;
		if (newHours > 24) {
			return "23:59";
		}
		// newHours = newHours - 24;
		String newHS = String.valueOf(newHours);
		if (newHS.length() < 2)
			newHS = "0" + newHS;
		return newHS + ":" + minS;
	}

	/**
	 * Return quarter of an hour begfore a time, e.g. "23:45" for "24:00"
	 * 
	 * @param time
	 * @return
	 */
	public static String getMinutesEarlier(String time, int minutes) {
		String[] parts = time.split(":");
		String hourS = parts[0];
		String minS = parts[1];
		int newHours = 0;
		int newMins = Integer.parseInt(minS) - minutes;
		if (newMins < 0) {
			newHours = Integer.parseInt(hourS) - 1;
			newMins = 60+newMins;
		} else {
			newHours = Integer.parseInt(hourS);
		}
		if (newHours < 0) {
			return "00:00";
		}
		return ensureTwoDigits(newHours) + ":" + ensureTwoDigits(newMins);
	}

	public static void main(String[] args) {
		System.out.println(addHoursToTime("05:00", 2));
		System.out.println(addHoursToTime("24:00", 2));
		System.out.println(getMinutesEarlier("24:00", 25));
		System.out.println(getMinutesEarlier("06:30",25));

	}

	/**
	 * Ensure that a string has two slots, e.g. 5 -> "05".
	 * 
	 * @param num
	 * @return
	 */
	public static String ensureTwoDigits(int num) {
		String digitString = String.valueOf(num);
		if (digitString.length() < 2)
			return "0" + digitString;
		return digitString;
	}
}

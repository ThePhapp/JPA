package com.javaweb.ultils;

public class NumbersUltil {
	public static boolean isNumber(String data) {
		try {
			Long number = Long.parseLong(data);
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}

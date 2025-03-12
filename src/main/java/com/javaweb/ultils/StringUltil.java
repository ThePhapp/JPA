package com.javaweb.ultils;

public class StringUltil {
	public static boolean checkString(String data) {
		if(data == null || data.equals("")) {
			return false;
		}
		else return true;
	}
}

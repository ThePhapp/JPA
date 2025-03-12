package com.javaweb.Model;

import java.util.ArrayList;

public class ErrorResponseDTO {
	private String error;
	private ArrayList<String> details = new ArrayList<String> ();
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public ArrayList<String> getDetails() {
		return details;
	}
	public void setDetails(ArrayList<String> details) {
		this.details = details;
	}
}

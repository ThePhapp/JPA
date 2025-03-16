package com.javaweb.Model;

public class BuildingRequestDTO {
	private String name;
	
	private String ward;
	
	private String steet;
	
	private Long districtId;
	
	private Long rentPrice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getSteet() {
		return steet;
	}

	public void setSteet(String steet) {
		this.steet = steet;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}

	public Long getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(Long rentPrice) {
		this.rentPrice = rentPrice;
	}
	
}

package com.javaweb.converter;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.ultils.MapUltil;

@Component
public class BuildingSearchBuilderConverter {

	public BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> typeCode) {
		BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder().setName(MapUltil.getObject(params, "name", String.class))
																							.setFloorArea(MapUltil.getObject(params, "floorArea", Long.class))
																							.setward(MapUltil.getObject(params, "ward", String.class))
																							.setStreet (MapUltil.getObject(params, "street", String.class)) 
																							.setDistrictId(MapUltil.getObject(params, "districtId", Long.class)) 
																							.setNumberOfBasement (MapUltil.getObject(params, "numberOfBasement", Integer.class)) 
																							.setTypeCode(typeCode) 
																							.setManagerName(MapUltil.getObject(params, "managerName", String.class)) 
																							.setManagerPhoneNumber (MapUltil.getObject(params, "managerphonenumber", String.class)) 
																							.setRentPriceTo(MapUltil.getObject(params, "rentPriceTo", Long.class)) 
																							.setRentPriceFrom(MapUltil.getObject(params, "rentPriceFrom", Long.class)) 
																							.setAreaFrom(MapUltil.getObject(params, "areaFrom", Long.class)) 
																							.setAreaTo(MapUltil.getObject (params, "areaTo", Long.class)) 
																							.setStaffId(MapUltil.getObject(params, "staffId", Long.class)) 
																							.build();
			return buildingSearchBuilder;
	}
}

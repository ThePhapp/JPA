package com.javaweb.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.repository.Entity.DistrictEntity;
import com.javaweb.repository.Entity.RentAreaEntity;

@Component
public class BuildingDTOConverter {
	@Autowired
	private ModelMapper modelMapper;
	
	public BuildingDTO toBuildingDTO(BuildingEntity item) {
		// TODO Auto-generated method stub
		BuildingDTO buildingDTO = modelMapper.map(item, BuildingDTO.class);
			buildingDTO.setAddress(item.getStreet() + "," + item.getWard() + ", " + item.getDistrict().getName());
			List<RentAreaEntity> rentAreas = item.getItems();
			String areaResult = rentAreas.stream().map(it->it.getValue().toString()).collect(Collectors.joining(","));
			buildingDTO.setRentArea(areaResult);
		return buildingDTO;
	}
}

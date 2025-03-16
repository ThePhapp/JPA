package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.Model.BuildingRequestDTO;
import com.javaweb.customException.FieldRequiredException;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.repository.Entity.DistrictEntity;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuildings(@RequestParam Map<String, Object> params,
											@RequestParam(name = "typecode", required = false) List<String> typecode) {
		List<BuildingDTO> result = buildingService.findAll(params, typecode);
				return result;
	}
	
	@PostMapping(value = "/api/building/")
	@Transactional
	public void createBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		buildingEntity.setStreet(buildingRequestDTO.getSteet());
		DistrictEntity district = new DistrictEntity();
		district.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(district);
		buildingEntity.setRentPrice(buildingRequestDTO.getRentPrice());
		
		entityManager.persist(buildingEntity);
	}
	
	@PutMapping(value = "/api/building/")
	@Transactional
	public void updateBuilding(@RequestBody BuildingRequestDTO buildingRequestDTO) {
		BuildingEntity buildingEntity = new BuildingEntity();
		buildingEntity.setId(3L);
		buildingEntity.setName(buildingRequestDTO.getName());
		buildingEntity.setWard(buildingRequestDTO.getWard());
		buildingEntity.setStreet(buildingRequestDTO.getSteet());
		DistrictEntity district = new DistrictEntity();
		district.setId(buildingRequestDTO.getDistrictId());
		buildingEntity.setDistrict(district);
		buildingEntity.setRentPrice(buildingRequestDTO.getRentPrice());
		
		entityManager.merge(buildingEntity);
	}
	
	@DeleteMapping(value = "/api/building/{id}")
	@Transactional
	private void deleteBuilding(@PathVariable Long id) {
		BuildingEntity buildingEntity = entityManager.find(BuildingEntity.class, id);
		entityManager.remove(buildingEntity);
	}
}

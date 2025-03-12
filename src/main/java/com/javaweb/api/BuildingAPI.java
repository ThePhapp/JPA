package com.javaweb.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.customException.FieldRequiredException;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {

	@Autowired
	private BuildingService buildingService;
	
	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuildings(@RequestParam Map<String, Object> params,
											@RequestParam(name = "typecode", required = false) List<String> typecode) {
		List<BuildingDTO> result = buildingService.findAll(params, typecode);
				return result;
	}
}

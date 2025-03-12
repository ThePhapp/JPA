package com.javaweb.repository;

import com.javaweb.repository.Entity.DistrictEntity;

public interface DistrictRepository {
	DistrictEntity findNameById(Long id);
}

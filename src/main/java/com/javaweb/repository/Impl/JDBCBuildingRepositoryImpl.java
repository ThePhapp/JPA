package com.javaweb.repository.Impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContexts;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaweb.builder.BuildingSearchBuilder;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.Entity.BuildingEntity;
import com.javaweb.ultils.ConnectionJDBCUltil;
import com.javaweb.ultils.NumbersUltil;
import com.javaweb.ultils.StringUltil;

@Repository
@PropertySource("classpath:application.properties")
public class JDBCBuildingRepositoryImpl implements BuildingRepository {

	@Value("${spring.datasource.url}") 
		private String DB_URL; 
	
	@Value("${spring.datasource.username}")
		private String USER; 
	
	@Value("${spring.datasource.password}") 
		private String PASS;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	public static void joinTable(BuildingSearchBuilder buildingSearchBuilder, StringBuilder sql) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			sql.append(" INNER JOIN assignmentbuilding ON b.id = assignmentbuilding.buildingid ");
		}
		
		List<String> typeCode = buildingSearchBuilder.getTypeCode();
		if(typeCode != null && typeCode.size() != 0) {
			sql.append(" INNER JOIN buildingrenttype ON b.id = buildingrenttype.buildingid ");
			sql.append(" INNER JOIN renttype ON renttype.id = buildingrenttype.renttypeid ");
		}
		
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if(rentAreaTo != null || rentAreaFrom != null) {
			sql.append(" INNER JOIN rentarea ON rentarea.buildingid = b.id ");
		}
	}
	
	public static void queryNormal(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		try {
			Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
			for(Field item : fields) {
				item.setAccessible(true);
				String fieldName = item.getName();
				if(!fieldName.equals("staffId") && !fieldName.equals("typecode") && 
						!fieldName.startsWith("area") && !fieldName.startsWith("rentPrice")) {
					Object value = item.get(buildingSearchBuilder);
					if(value != null) {
						if(item.getType().getName().equals("java.lang.Long") || item.getType().getName().equals("java.lang.Integer")) {
							where.append(" AND b. " + fieldName + " = " + value);
						} else if(item.getType().getName().equals("java.lang.String")){
							where.append(" AND b. " + fieldName + " LIKE '%" + value + "%'");
						}
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void querySpecial(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
		Long staffId = buildingSearchBuilder.getStaffId();
		if(staffId != null) {
			where.append(" AND assignmentbuilding.staffid = " + staffId);
		}
		Long rentAreaTo = buildingSearchBuilder.getAreaTo();
		Long rentAreaFrom = buildingSearchBuilder.getAreaFrom();
		if(rentAreaTo != null || rentAreaFrom != null) {
			if(rentAreaFrom != null) {
				where.append(" AND rentarea.value >= " + rentAreaFrom);
			}
			if(rentAreaTo != null) {
				where.append(" AND rentarea.value <= " + rentAreaTo);
			}
		}
		
		Long rentPriceFrom = buildingSearchBuilder.getRentPriceFrom();
		Long rentPriceTo = buildingSearchBuilder.getRentPriceTo();
		if(rentPriceTo != null || rentPriceFrom != null) {
			if(rentPriceFrom != null) {
				where.append(" AND b,rentprice >= " + rentPriceFrom);
			}
			if(rentPriceTo != null) {
				where.append(" AND b.rentprice <= " + rentPriceTo);
			}
		}
		List<String> typecode = buildingSearchBuilder.getTypeCode();
		if(typecode != null && typecode.size() != 0) {
			where.append(" AND( ");
			String sql = typecode.stream().map(it-> " renttype.code Like" + "'%" + it + "%' ").collect(Collectors.joining(" OR "));
			where.append(sql);
			where.append(" ) ");
		}
	}
	
	@Override
	public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
		StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.districtid, b.street, b.ward, b.numberofbasement, b.floorarea, "
												+ " b.managername, b.managerphonenumber, b.servicefee, b.brokeragefee FROM building b ");
		joinTable(buildingSearchBuilder, sql);
		StringBuilder where = new StringBuilder(" WHERE 1=1 ");
		queryNormal(buildingSearchBuilder, where);
		querySpecial(buildingSearchBuilder, where);
		sql.append(" GROUP BY b.id;");
		sql.append(where);
		Query query = entityManager.createNativeQuery(sql.toString(), BuildingEntity.class);
		return query.getResultList();
  } 
}

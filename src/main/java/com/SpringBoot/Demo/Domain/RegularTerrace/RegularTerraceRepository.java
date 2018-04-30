package com.SpringBoot.Demo.Domain.RegularTerrace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegularTerraceRepository extends JpaRepository<RegularTerrace, Long>{
	
	
	@Query("SELECT rt " +
			"FROM RegularTerrace rt " +
			"WHERE terrace_number= :terrace_number")
	public RegularTerrace findOneByRegularTerraceNumber(@Param("terrace_number")Long terrace_number);
}

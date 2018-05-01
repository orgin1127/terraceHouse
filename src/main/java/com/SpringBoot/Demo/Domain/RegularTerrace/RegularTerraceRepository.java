package com.SpringBoot.Demo.Domain.RegularTerrace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegularTerraceRepository extends JpaRepository<RegularTerrace, Long>{
	
	
	@Query("SELECT rt " +
			"FROM RegularTerrace rt " +
			"WHERE regular_terrace_number= :regular_terrace_number")
	public RegularTerrace findOneByRegularTerraceNumber(@Param("regular_terrace_number")Long terrace_number);
}

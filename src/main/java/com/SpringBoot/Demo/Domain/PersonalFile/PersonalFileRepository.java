package com.SpringBoot.Demo.Domain.PersonalFile;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonalFileRepository extends JpaRepository<PersonalFile, Long>{
	
	@Query("SELECT pf " + 
			"FROM PersonalFile pf " + 
			"WHERE pf.member_number= :member_number")
	Stream<PersonalFile> findAllByMemberNumber(@Param("member_number") Long member_number);
}

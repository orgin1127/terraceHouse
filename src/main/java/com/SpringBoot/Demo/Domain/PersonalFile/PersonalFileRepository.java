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
	
	@Query("UPDATE PersonalFile " +
			"SET saved_personal_file_path= :saved_personal_file_path" +
			", saved_personal_file_name= :saved_personal_file_name " +
			"WHERE terrace_room_number= :terrace_room_number and " +
			"member_number= :member_number")
	int updateByPersonalFileNumber(@Param("saved_personal_file_path") String saved_personal_file_path
									, @Param("saved_personal_file_name") String saved_personal_file_name
									, @Param("terrace_room_number") Long terrace_room_number
									, @Param("member_number") Long member_number);
}

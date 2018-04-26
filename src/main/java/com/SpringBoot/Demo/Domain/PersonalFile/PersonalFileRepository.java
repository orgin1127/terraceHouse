package com.SpringBoot.Demo.Domain.PersonalFile;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.SpringBoot.Demo.Domain.Member.Member;
import com.SpringBoot.Demo.Domain.TerraceRoom.TerraceRoom;

public interface PersonalFileRepository extends JpaRepository<PersonalFile, Long>{
	
	@Query("SELECT pf " + 
			"FROM PersonalFile pf " + 
			"WHERE pf.member_number= :member_number and " + 
			"(pf.terrace_room_number.saved_file_path != 'non' and pf.saved_personal_file_path != 'non') " + 
			"ORDER BY file_number DESC")
	Stream<PersonalFile> findAllByMemberNumber(@Param("member_number") Member member_number);
	
	@Modifying
	@Query("UPDATE PersonalFile " +
			"SET saved_personal_file_path= :saved_personal_file_path" +
			", saved_personal_file_name= :saved_personal_file_name " +
			"WHERE terrace_room_number= :terrace_room_number and " +
			"member_number= :member_number")
	int updateByPersonalFileNumber(@Param("saved_personal_file_path") String saved_personal_file_path
									, @Param("saved_personal_file_name") String saved_personal_file_name
									, @Param("terrace_room_number") TerraceRoom terrace_room_number
									, @Param("member_number") Member member_number);
	
}

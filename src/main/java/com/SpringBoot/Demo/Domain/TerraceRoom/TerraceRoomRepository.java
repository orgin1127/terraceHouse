package com.SpringBoot.Demo.Domain.TerraceRoom;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TerraceRoomRepository extends JpaRepository<TerraceRoom, Long> {
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " + 
			"ORDER BY tr.terrace_room_number ASC")
	Stream<TerraceRoom> findAllAsc();
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " +
			"WHERE terrace_room_active= 'active'" + 
			"ORDER BY tr.terrace_room_number DESC")
	Stream<TerraceRoom> findAllByTerraceActive();

	@Query("SELECT tr " +
			"FROM TerraceRoom tr " +
			"WHERE tr.terrace_room_name LIKE CONCAT ('%',:inputTitle,'%')")
	Stream<TerraceRoom> findAllByTitle(@Param("inputTitle") String inputTitle);
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " + 
			"WHERE tr.terrace_room_number= :terrace_room_number")
	TerraceRoom findOneByTerraceRoomNumber(@Param("terrace_room_number") Long terrace_room_number);
	
	@Modifying
	@Query("UPDATE TerraceRoom " + 
			"SET original_file_name= :original_file_name" +
			", saved_file_path= :saved_file_path" + 
			", saved_file_name= :saved_file_name " +
			", shared_file_path= :shared_file_path " +
			", shared_file_name= :shared_file_name " +
			"WHERE terrace_room_number= :terrace_room_number")
	int updateTerraceRoomInPDF(@Param("terrace_room_number") Long terrace_room_number
								,@Param("original_file_name") String original_file_name
								,@Param("saved_file_path") String saved_file_path
								,@Param("saved_file_name") String saved_file_name
								,@Param("shared_file_path") String shared_file_path
								,@Param("shared_file_name") String shared_file_name);
	
	@Modifying
	@Query("UPDATE TerraceRoom " + 
			"SET terrace_room_active= 'inactive' " +
			"WHERE terrace_room_number= :terrace_room_number")
	int updateTerraceRoomInActive(@Param("terrace_room_number") Long terrace_room_number);
	
}

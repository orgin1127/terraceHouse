package com.SpringBoot.Demo.Domain.TerraceRoom;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TerraceRoomRepository extends JpaRepository<TerraceRoom, Long> {
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " + 
			"ORDER BY tr.terrace_room_number ASC")
	Stream<TerraceRoom> findAllAsc();
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " + 
			"WHERE tr.terrace_room_number= :terrace_room_number")
	TerraceRoom findOneByMemberNumber(@Param("terrace_room_number") Long terrace_room_number);
}

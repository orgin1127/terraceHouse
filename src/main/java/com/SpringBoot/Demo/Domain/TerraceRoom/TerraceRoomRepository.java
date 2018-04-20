package com.SpringBoot.Demo.Domain.TerraceRoom;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TerraceRoomRepository extends JpaRepository<TerraceRoom, Long> {
	
	@Query("SELECT tr " + 
			"FROM TerraceRoom tr " + 
			"ORDER BY tr.terrace_room_number ASC")
	Stream<TerraceRoom> findAllAsc();
}

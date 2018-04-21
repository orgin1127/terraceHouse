package com.SpringBoot.Demo.Domain.JoinRoomMember;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JoinRoomMemberRepository extends JpaRepository<JoinRoomMember, Long> {
	
	@Query("SELECT jrm " + 
			"FROM JoinRoomMember jrm " + 
			"WHERE join_member_number.member_number= :join_member_number")
	Stream<JoinRoomMember> findOneByMemberNumber(@Param("join_member_number") Long join_member_number);
	
}

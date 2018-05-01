package com.SpringBoot.Demo.Domain.RegularTerraceMember;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegularTerraceMemberRepository extends JpaRepository<RegularTerraceMember, Long> {
	
	
	@Query("SELECT rtm " +
			"FROM RegularTerraceMember rtm " +
			"WHERE member_number= :member_number")
	public ArrayList<RegularTerraceMember>findAllRegularTerraceByMemberNumber(@Param("member_number")Long member_number);
}

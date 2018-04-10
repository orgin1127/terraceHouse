package com.SpringBoot.Demo.Domain.Member;


import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	@Query("SELECT m " + 
			"FROM Member m " + 
			"ORDER BY m.member_number DESC")
	Stream<Member> findAllDesc();
	
	@Query("SELECT m " +
			"FROM Member m " + 
			"WHERE m.memberid= :memberid")
	Member findById(@Param("memberid") String memberid);
	
	/*@Modifying
	@Query("UPDATE Member " + 
			"set email_confirmed= 'y' " +
			"WHERE ")
	Member updateMemberEmailConfirmed();*/
	
	@Query("SELECT m " +
			"FROM Member m " + 
			"WHERE m.memberid= :memberid and m.memberpw= :memberpw")
	Member findByIdAndPw(@Param("memberid") String memberid, @Param("memberpw") String memberpw);
}

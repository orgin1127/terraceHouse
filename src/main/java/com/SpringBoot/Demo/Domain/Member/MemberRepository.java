package com.SpringBoot.Demo.Domain.Member;


import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
	
	@Query("SELECT m " + 
			"FROM Member m " + 
			"ORDER BY m.member_number DESC")
	Stream<Member> findAllDesc();
}

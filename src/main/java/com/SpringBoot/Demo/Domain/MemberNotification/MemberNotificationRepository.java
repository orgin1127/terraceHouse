package com.SpringBoot.Demo.Domain.MemberNotification;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {
	
	
	@Query("SELECT mn " + 
			"FROM MemberNotification mn " + 
			"WHERE receiver= :receiver AND confirmed != 'y'")
	public ArrayList<MemberNotification> getNotificationList(@Param("receiver")String receiverID);
	
	@Modifying
	@Query("UPDATE MemberNotification " +
			"SET confirmed= 'y' " +
			"WHERE receiver= :receiver AND " +
			"terrace_number= :terrace_number")
	public void updateNotificationConfirmed(@Param("terrace_number")Long terrace_number,@Param("receiver") String receiver);
}

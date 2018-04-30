package com.SpringBoot.Demo.Domain.MemberNotification;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberNotificationRepository extends JpaRepository<MemberNotification, Long> {
	
	
	@Query("SELECT mn " + 
			"FROM MemberNotification mn " + 
			"WHERE receiver= :receiver")
	public ArrayList<MemberNotification> getNotificationList(@Param("receiver")String receiverID);
}

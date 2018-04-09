package com.SpringBoot.Demo.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.SpringBoot.Demo.Domain.TerraceMember;
import com.SpringBoot.Demo.Domain.TerraceMemberRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TerraceMemberService {
	
	private TerraceMemberRepository tmr;
	
	private static Logger logger = LoggerFactory.getLogger(TerraceMemberService.class);
	
	@Transactional
	public TerraceMember terraceMemberSave(TerraceMember tm) {
		TerraceMember savedEntity = tmr.saveAndFlush(tm);
		logger.debug("tm:" + tm.toString());
		logger.debug("save:"+savedEntity.toString());
		return savedEntity;
	}
}

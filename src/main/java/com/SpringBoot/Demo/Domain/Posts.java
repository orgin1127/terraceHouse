package com.SpringBoot.Demo.Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//기존의 VO class 역할을 하면서 동시에 DB와 XML 없이 곧바로 데이터를 주고받는다
//그렇기 때문에 직접적으로 Entity class(DB Layer)를 res, req로 써서는 안되며 
//구성이 동일한 DTO(View Layer)를 만들어 그쪽에서 controller로 데이터를 Entity로 넘기게 해야한다.
@Entity
public class Posts extends BaseTimeEntity{
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(length = 500, nullable = false)
	private String title;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;
	
	private String author;
	
	@Builder
	public Posts(String title, String content, String author){
		this.title = title;
		this.content = content;
		this.author = author;
	}
}

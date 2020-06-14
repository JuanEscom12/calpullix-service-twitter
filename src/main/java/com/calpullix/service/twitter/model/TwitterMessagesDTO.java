package com.calpullix.service.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TwitterMessagesDTO {
	
	private String header;
	
	private String name;
	
	private String atName;
	
	private String date;
	
	private String hashTags;
	
	private String message;
	
}

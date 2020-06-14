package com.calpullix.service.twitter.model;

import java.util.List;

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
public class TwitterResponseDTO {
	
	private byte[] perfilPicture;
	
	private byte[] wordCloud;
	
	private String namePerfil;
	
	private String atName;
	
	private List<String> keyWords;
	
	private List<GraphicDetailDTO> graphic;
	
	private List<TwitterMessagesDTO> positiveMessages;
	
	private List<TwitterMessagesDTO> negativeMessages;
	
	private List<TwitterMessagesDTO> neutralMessages;
	
	private Boolean started;
	
}

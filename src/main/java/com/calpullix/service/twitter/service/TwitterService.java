package com.calpullix.service.twitter.service;

import com.calpullix.service.twitter.model.TwitterRequestDTO;
import com.calpullix.service.twitter.model.TwitterResponseDTO;

public interface TwitterService {

	TwitterResponseDTO getTwittersAnalysis(TwitterRequestDTO request);
	
	TwitterResponseDTO startAnalysis();
	
}

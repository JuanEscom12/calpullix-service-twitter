package com.calpullix.service.twitter.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.twitter.model.TwitterRequestDTO;
import com.calpullix.service.twitter.service.TwitterService;
import com.calpullix.service.twitter.util.AbstractWrapper;
import com.calpullix.service.twitter.util.ValidationHandler;

import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TwitterHandler {

	@Autowired
	private TwitterService twitterService;
	
	@Autowired
	private ValidationHandler validationHandler;

	@Value("${app.message-error-location-body}")
	private String messageErrorLocationBody;

	@Timed(value = "calpullix.service.twitter.metrics", description = "Retrieve Twitters ")
	public Mono<ServerResponse> getTwitters(ServerRequest serverRequest) {
		log.info(":: Retrieve Twitter Handler {} ", serverRequest);
		return validationHandler.handle(
				input -> input
						.flatMap(request -> AbstractWrapper.async(() -> twitterService.getTwittersAnalysis(request)
		)).flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response))), serverRequest,
				TwitterRequestDTO.class).switchIfEmpty(Mono.error(new Exception(messageErrorLocationBody)));
	}
	
	@Timed(value = "calpullix.service.start.analysis.twitter.metrics", description = "Start analysis twitters ")
	public Mono<ServerResponse> startAnalysis(ServerRequest serverRequest) {
		log.info(":: Start Analysis Handler {} ", serverRequest);
		return AbstractWrapper.async(() -> twitterService.startAnalysis())
				.flatMap(response -> ServerResponse.ok().body(BodyInserters.fromObject(response)));
	}
	
}

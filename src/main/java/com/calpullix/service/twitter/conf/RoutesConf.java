package com.calpullix.service.twitter.conf;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.calpullix.service.twitter.handler.TwitterHandler;

@Configuration
public class RoutesConf {

	@Value("${app.path-retrieve-twitter}")
	private String pathRetrieveTwitter;
		
	@Value("${app.path-start-analysis}")
	private String pathStartAnalysis;
	
	@Bean
	public RouterFunction<ServerResponse> routesLogin(TwitterHandler twitterHandler) {
		return route(POST(pathRetrieveTwitter), twitterHandler::getTwitters)
				.and(route(GET(pathStartAnalysis), twitterHandler::startAnalysis));
	}
	
}

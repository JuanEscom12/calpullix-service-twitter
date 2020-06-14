package com.calpullix.service.twitter.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.calpullix.db.process.catalog.model.TwitterTypeMessage;
import com.calpullix.db.process.twitter.model.Twitter;
import com.calpullix.db.process.twitter.model.TwitterMessages;
import com.calpullix.db.process.twitter.repository.TwitterMessagesRepository;
import com.calpullix.db.process.twitter.repository.TwitterRepository;
import com.calpullix.service.twitter.model.GraphicDetailDTO;
import com.calpullix.service.twitter.model.TwitterMessagesDTO;
import com.calpullix.service.twitter.model.TwitterRequestDTO;
import com.calpullix.service.twitter.model.TwitterResponseDTO;
import com.calpullix.service.twitter.service.TwitterService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TwitterServiceImpl implements TwitterService {

	private static final String LABEL_POSITIVE_TWITTERS = "Twitters positivos";

	private static final String LABEL_NEGATIVE_TWITTERS = "Twitters negativos";

	private static final String LABEL_NEUTRAL_TWITTERS = "Twitters neutrales";

	private static final String LABEL_PERCENTAGE_POSITIVE_TWITTERS = "% Positivos";

	private static final String LABEL_PERCENTAGE_NEGATIVE_TWITTERS = "% Negativos";

	private static final String LABEL_PERCENTAGE_NEUTRAL_TWITTERS = "% Neutral";
	
	private static final int LIMIT_MESSAGES = 5;

	@Autowired
	private TwitterRepository twitterRepository;

	@Autowired
	private TwitterMessagesRepository twitterMessagesRepository;

	@Override
	public TwitterResponseDTO getTwittersAnalysis(TwitterRequestDTO request) {
		log.info(":: Service getTwittersAnalysis {} ", request);

		final Optional<Twitter> twitter = twitterRepository.findOneByIsactive(Boolean.TRUE);
		if (BooleanUtils.negate(twitter.isPresent())) {
			return new TwitterResponseDTO();
		}
		final TwitterResponseDTO result = new TwitterResponseDTO();
		result.setNamePerfil(twitter.get().getProfilename());
		result.setAtName(twitter.get().getProfilattname());
		result.setPerfilPicture(twitter.get().getProfilepicture());
		result.setWordCloud(twitter.get().getClowwords());
		final List<String> keyWords = new ArrayList<>();
		keyWords.add(twitter.get().getKeywordone());
		keyWords.add(twitter.get().getKeywordtwo());
		keyWords.add(twitter.get().getKeywordthree());
		keyWords.add(twitter.get().getKeywordfour());
		keyWords.add(twitter.get().getKeywordfive());
		result.setKeyWords(keyWords);

		final Pageable pagination = PageRequest.of(0, LIMIT_MESSAGES);
		List<TwitterMessages> messages = twitterMessagesRepository.findAllByIdtwitterAndTypemessagevalue(twitter.get(),
				TwitterTypeMessage.POSITIVE.getId(), pagination);
		final List<TwitterMessagesDTO> listMessage = new ArrayList<>();

		messages.stream().forEach(item -> {
			final TwitterMessagesDTO message = new TwitterMessagesDTO();
			message.setHeader(LABEL_POSITIVE_TWITTERS);
			message.setDate(item.getDate());
			message.setMessage(item.getMessage());
			message.setName(item.getUser());
			message.setAtName(item.getAtuser());
			listMessage.add(message);
		});
		result.setPositiveMessages(listMessage);

		final List<TwitterMessagesDTO> listMessageNegative = new ArrayList<>();
		messages = twitterMessagesRepository.findAllByIdtwitterAndTypemessagevalue(twitter.get(),
				TwitterTypeMessage.NEGATIVE.getId(), pagination);
		messages.stream().forEach(item -> {
			final TwitterMessagesDTO message = new TwitterMessagesDTO();
			message.setHeader(LABEL_NEGATIVE_TWITTERS);
			message.setDate(item.getDate());
			message.setMessage(item.getMessage());
			message.setName(item.getUser());
			message.setAtName(item.getAtuser());
			listMessageNegative.add(message);
		});
		result.setNegativeMessages(listMessageNegative);

		final List<TwitterMessagesDTO> listMessageNeutral = new ArrayList<>();
		messages = twitterMessagesRepository.findAllByIdtwitterAndTypemessagevalue(twitter.get(),
				TwitterTypeMessage.NEUTRAL.getId(), pagination);
		messages.stream().forEach(item -> {
			final TwitterMessagesDTO message = new TwitterMessagesDTO();
			message.setHeader(LABEL_NEUTRAL_TWITTERS);
			message.setDate(item.getDate());
			message.setMessage(item.getMessage());
			message.setName(item.getUser());
			message.setAtName(item.getAtuser());
			listMessageNeutral.add(message);
		});
		result.setNeutralMessages(listMessageNeutral);		
		
		final List<GraphicDetailDTO> graphic = new ArrayList<>();
		GraphicDetailDTO positiveGraphic = new GraphicDetailDTO();
		positiveGraphic.setName(LABEL_PERCENTAGE_POSITIVE_TWITTERS);
		positiveGraphic.setValue(twitter.get().getPercentagepositive());
		GraphicDetailDTO negativeGraphic = new GraphicDetailDTO();
		negativeGraphic.setName(LABEL_PERCENTAGE_NEGATIVE_TWITTERS);
		negativeGraphic.setValue(twitter.get().getPercentagenegative());
		GraphicDetailDTO neutralGraphic = new GraphicDetailDTO();
		neutralGraphic.setName(LABEL_PERCENTAGE_NEUTRAL_TWITTERS);
		neutralGraphic.setValue(twitter.get().getPercentageneutral());
		graphic.add(positiveGraphic);
		graphic.add(negativeGraphic);
		graphic.add(neutralGraphic);
		log.info(":: GRAPHIC {} {} {} ", positiveGraphic, negativeGraphic, neutralGraphic);
		result.setGraphic(graphic);
		
		return result;
	}

	@Override
	public TwitterResponseDTO startAnalysis() {
		final TwitterResponseDTO result = new TwitterResponseDTO();
		result.setStarted(Boolean.TRUE);
		return result;
	}

}

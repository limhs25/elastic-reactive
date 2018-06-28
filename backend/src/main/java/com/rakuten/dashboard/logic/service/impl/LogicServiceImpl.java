package com.rakuten.dashboard.logic.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.inject.Inject;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.model.Logic;
import com.rakuten.dashboard.logic.repository.LogicRepository;
import com.rakuten.dashboard.logic.service.EmitterProcessorService;
import com.rakuten.dashboard.logic.service.LogicService;

import static com.rakuten.dashboard.logic.model.IndexType.Logic.Field.ACTIVE;
import static com.rakuten.dashboard.logic.model.IndexType.Logic.Field.LOGIC;
import static com.rakuten.dashboard.logic.model.IndexType.Logic.INDEX;
import static com.rakuten.dashboard.logic.model.IndexType.Logic.TYPE;
import static org.elasticsearch.common.Strings.isNullOrEmpty;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class LogicServiceImpl implements LogicService {
    private final LogicRepository logicRepository;
    private final ObjectMapper mapper;
    private final EmitterProcessorService processorService;

    @Inject
    public LogicServiceImpl(LogicRepository logicRepository, ObjectMapper mapper, EmitterProcessorService processorService) {
        this.logicRepository = logicRepository;
        this.mapper = mapper;
        this.processorService = processorService;
    }

    @Override
    public Flux<Logic> loadLogic(String query, Integer page, Integer size) {
        SearchRequest searchRequest = new SearchRequest(new String[]{INDEX},
                new SearchSourceBuilder().query(isNullOrEmpty(query) ? matchAllQuery() : matchQuery(LOGIC, query)));

        return logicRepository.search(searchRequest);
    }

    @Override
    public Mono<Logic> loadLogic(String id) {
        return logicRepository.loadOne(new GetRequest(INDEX, TYPE, id));
    }

    @Override
    public Mono<Logic> updateLogic(String id, Logic logic) {
        return loadLogic(id)
                .map(e -> new UpdateRequest(INDEX, TYPE, id).doc(ACTIVE, logic.getActive().toString()))
                .flatMap(logicRepository::update)
                .doOnSuccess(processorService::onNext);
    }

    @Override
    public Mono deleteLogic(String id) {
        return loadLogic(id).flatMap(e -> logicRepository.delete(new DeleteRequest(INDEX, TYPE, id)));
    }

    @Override
    public Mono createLogic(Logic logic) {
        String map = map(logic).orElseThrow(RuntimeException::new);

        IndexRequest request = new IndexRequest(INDEX, TYPE).source(map, XContentType.JSON);

        return logicRepository.save(request);
    }

    private Optional<String> map(Logic logic) {
        try {
            return Optional.of(mapper.writeValueAsString(logic));
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}

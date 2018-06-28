package com.rakuten.dashboard.logic.repository.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.inject.Inject;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.model.Logic;
import com.rakuten.dashboard.logic.repository.LogicRepository;

@Repository
public class LogicRepositoryImpl implements LogicRepository {
    private final RestHighLevelClient client;
    private final ObjectMapper mapper;

    @Inject
    public LogicRepositoryImpl(RestHighLevelClient client, ObjectMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    @Override
    public Flux<Logic> search(SearchRequest query) {
        return Flux.create(sink ->
                client.searchAsync(query, ActionListener.wrap(e -> Flux.just(e.getHits().getHits())
                        .map(x -> map(x.getSourceAsString(), x.getId()))
                        .doOnComplete(sink::complete)
                        .subscribe(sink::next), sink::error)));
    }

    @Override
    public Mono<Logic> loadOne(GetRequest request) {
        return Mono.create(sink -> client.getAsync(request, ActionListener.wrap(e ->
                sink.success(map(e.getSourceAsString(), e.getId())), sink::error)));
    }

    @Override
    public Mono save(IndexRequest index) {
        return Mono.create(sink -> client.indexAsync(index, ActionListener.wrap(sink::success, sink::error)));
    }

    @Override
    public Mono<Logic> update(UpdateRequest index) {
        return Mono.create(sink -> client.updateAsync(index, ActionListener.wrap(e -> sink.success(), sink::error)));
    }

    @Override
    public Mono<String> delete(DeleteRequest index) {
        return Mono.create(sink -> client.deleteAsync(index, ActionListener.wrap(e -> sink.success(), sink::error)));
    }

    private Logic map(String e, String id) {
        try {
            Logic logic = mapper.readValue(e, Logic.class);
            logic.setId(id);
            return logic;
        } catch (IOException e1) {
            return null;
        }
    }
}

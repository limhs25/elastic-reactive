package com.rakuten.dashboard.logic.repository;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.update.UpdateRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.model.Logic;

public interface LogicRepository {
    Flux<Logic> search(SearchRequest query);

    Mono save(IndexRequest index);

    Mono<Logic> loadOne(GetRequest getRequest);

    Mono<Logic> update(UpdateRequest index);

    Mono<String> delete(DeleteRequest index);
}

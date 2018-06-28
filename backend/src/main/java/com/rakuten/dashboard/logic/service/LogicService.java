package com.rakuten.dashboard.logic.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.model.Logic;

public interface LogicService {
    Flux<Logic> loadLogic(String query, Integer page, Integer size);

    Mono<Logic> loadLogic(String id);

    Mono updateLogic(String id, Logic active);

    Mono deleteLogic(String id);

    Mono createLogic(Logic logic);
}

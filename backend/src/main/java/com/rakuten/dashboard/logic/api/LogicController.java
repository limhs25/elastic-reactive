package com.rakuten.dashboard.logic.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.model.Logic;

@RequestMapping(value = "/api/logic")
public interface LogicController {
    @GetMapping(value = "/stream", produces = "text/event-stream")
    Flux<Logic> stream();

    @GetMapping
    Flux<Logic> loadLogic(String query, Integer page, Integer size);

    @GetMapping("/{id}")
    Mono<Logic> loadLogic(String id);

    @PutMapping("/{id}")
    Mono updateLogic(String id, Logic logic);

    @DeleteMapping("/{id}")
    Mono deleteLogic(String id);

    @PostMapping
    Mono createLogic(Logic logic);
}

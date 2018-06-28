package com.rakuten.dashboard.logic.api.impl;

import org.elasticsearch.common.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.rakuten.dashboard.logic.api.LogicController;
import com.rakuten.dashboard.logic.model.Logic;
import com.rakuten.dashboard.logic.service.EmitterProcessorService;
import com.rakuten.dashboard.logic.service.LogicService;

@RestController
public class LogicControllerImpl implements LogicController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LogicService logicService;
    private final EmitterProcessorService processorService;

    @Inject
    public LogicControllerImpl(LogicService logicService, EmitterProcessorService processorService) {
        this.logicService = logicService;
        this.processorService = processorService;
    }

    @Override
    public Flux<Logic> stream() {
        logger.debug("new subscription");
        return processorService.getProcessor();
    }

    @Override
    public Flux<Logic> loadLogic(@RequestParam("q") String query,
                                 @RequestParam(value = "p", defaultValue = "0") Integer page,
                                 @RequestParam(value = "s", defaultValue = "10") Integer size) {
        logger.debug("Load logic query: " + query);
        return logicService.loadLogic(query, page, size);
    }

    @Override
    public Mono updateLogic(@PathVariable("id") String id, @RequestBody Logic logic) {
        logger.debug("update logic id: " + id);
        return logicService.updateLogic(id, logic);
    }

    @Override
    public Mono deleteLogic(@PathVariable("id") String id) {
        return logicService.deleteLogic(id);
    }

    @Override
    public Mono<Logic> loadLogic(@PathVariable("id") String id) {
        return logicService.loadLogic(id);
    }

    @Override
    public Mono createLogic(@RequestBody Logic logic) {
        return logicService.createLogic(logic);
    }
}

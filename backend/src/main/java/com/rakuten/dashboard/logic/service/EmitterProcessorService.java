package com.rakuten.dashboard.logic.service;

import reactor.core.publisher.EmitterProcessor;

import com.rakuten.dashboard.logic.model.Logic;

public interface EmitterProcessorService {
    EmitterProcessor<Logic> getProcessor();

    void onNext(Logic logic);
}

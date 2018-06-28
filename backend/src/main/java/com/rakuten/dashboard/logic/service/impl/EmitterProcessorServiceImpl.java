package com.rakuten.dashboard.logic.service.impl;

import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;

import com.rakuten.dashboard.logic.model.Logic;
import com.rakuten.dashboard.logic.service.EmitterProcessorService;

@Service
public class EmitterProcessorServiceImpl implements EmitterProcessorService {
    private EmitterProcessor<Logic> processor;

    public EmitterProcessorServiceImpl(){
        processor = EmitterProcessor.create(300, false);
    }

    @Override
    public EmitterProcessor<Logic> getProcessor() {
        return processor;
    }

    @Override
    public void onNext(Logic logic) {
        processor.onNext(logic);
    }
}

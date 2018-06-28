package com.rakuten.dashboard.logic.model;

import java.util.List;

public class ScrollResponse<T> {
    public List<T> payload;
    public Integer scrollId;

    public List<T> getPayload() {
        return payload;
    }

    public void setPayload(List<T> payload) {
        this.payload = payload;
    }

    public Integer getScrollId() {
        return scrollId;
    }

    public void setScrollId(Integer scrollId) {
        this.scrollId = scrollId;
    }
}

package com.rakuten.dashboard.logic.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchConfig {
    @Bean
    public RestHighLevelClient restHighLevelClient(ElasticsearchProperties props) {
        HttpHost[] hosts = props.getClusterNodes().stream()
                .map(e -> {
                    String[] host = e.split(":");
                    return new HttpHost(host[0], Integer.valueOf(host[1]));
                })
                .toArray(HttpHost[]::new);
        return new RestHighLevelClient(RestClient.builder(hosts)
                        .setRequestConfigCallback(config -> config
                                .setConnectTimeout(5_000)
                                .setConnectionRequestTimeout(5_000)
                                .setSocketTimeout(5_000))
                        .setMaxRetryTimeoutMillis(5_000));
    }
}

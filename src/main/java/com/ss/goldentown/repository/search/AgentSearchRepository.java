package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Agent;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Agent entity.
 */
public interface AgentSearchRepository extends ElasticsearchRepository<Agent, Long> {
}

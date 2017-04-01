package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Deal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Deal entity.
 */
public interface DealSearchRepository extends ElasticsearchRepository<Deal, Long> {
}

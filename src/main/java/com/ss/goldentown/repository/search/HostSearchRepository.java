package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Host;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Host entity.
 */
public interface HostSearchRepository extends ElasticsearchRepository<Host, Long> {
}

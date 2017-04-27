package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.World;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the World entity.
 */
public interface WorldSearchRepository extends ElasticsearchRepository<World, Long> {
}

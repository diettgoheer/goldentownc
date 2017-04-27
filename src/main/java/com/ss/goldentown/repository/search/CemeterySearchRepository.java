package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Cemetery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cemetery entity.
 */
public interface CemeterySearchRepository extends ElasticsearchRepository<Cemetery, Long> {
}

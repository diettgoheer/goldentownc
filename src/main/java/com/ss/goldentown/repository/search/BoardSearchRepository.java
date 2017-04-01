package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Board;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Board entity.
 */
public interface BoardSearchRepository extends ElasticsearchRepository<Board, Long> {
}

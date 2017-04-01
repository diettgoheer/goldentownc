package com.ss.goldentown.repository.search;

import com.ss.goldentown.domain.Billing;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Billing entity.
 */
public interface BillingSearchRepository extends ElasticsearchRepository<Billing, Long> {
}

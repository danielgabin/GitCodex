package es.raiseb.gitcodex.githubsearch;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.MoreLikeThisQuery;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

@Component
public class GithubSearchProjectRepository {

	@Autowired
	ElasticsearchOperations elasticOps;

	public List<String> findProjects(String[] filesContent) {
		String[] fields = new String[1];
		fields[0] = "file_content";
		QueryBuilder qb = QueryBuilders.matchAllQuery();

		qb = QueryBuilders.moreLikeThisQuery(fields, filesContent, null).minTermFreq(1).maxQueryTerms(12);

		SearchResponse sr = elasticOps.getClient().prepareSearch().setSize(0).setQuery(qb)
				.addAggregation(AggregationBuilders.terms("topRepository").field("repository_url.keyword")).execute()
				.actionGet();

		// Get your facet results
		Terms topRepository = sr.getAggregations().get("topRepository");
		List<String> repositories = new ArrayList<String>();
		for (Terms.Bucket bucket : topRepository.getBuckets()) {
			repositories.add(bucket.getKey().toString());
		}

		return repositories;
	}

}

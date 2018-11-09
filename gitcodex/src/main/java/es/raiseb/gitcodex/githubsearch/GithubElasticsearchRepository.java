package es.raiseb.gitcodex.githubsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import es.raiseb.gitcodex.file.CodeFile;

public interface GithubElasticsearchRepository extends ElasticsearchRepository<CodeFile, String> {
}

package es.raiseb.gitcodex.githubsearch;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import es.raiseb.gitcodex.file.CodeFile;

public interface GithubElasticsearchRepository extends ElasticsearchRepository<CodeFile, String> {

	@Query("{\"more_like_this\" : {\"fields\" : [\"file_content\"],\"like\" : [\"?0\"],\"min_term_freq\" : 1,\"min_doc_freq\":1}}")
	Page<CodeFile> findByCodeFileContent(String codeFileContent, Pageable page);
}

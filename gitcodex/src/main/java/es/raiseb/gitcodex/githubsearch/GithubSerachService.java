package es.raiseb.gitcodex.githubsearch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.raiseb.gitcodex.file.CodeFile;

@Service
public class GithubSerachService {

	@Autowired
	GithubElasticsearchRepository repository;

	@Transactional
	public List<CodeFile> searchOnGithub(CodeFile file) {
		List<CodeFile> mockFiles = new ArrayList<CodeFile>();

		mockFiles.add(repository.findById("1").get());

		return mockFiles;
	}

}

package es.raiseb.gitcodex;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import es.raiseb.gitcodex.file.File;
import es.raiseb.gitcodex.githubsearch.GithubSerachService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitcodexApplicationTests {

	@Autowired
	private GithubSerachService githubSearchService;

	@Test
	public void test() {

		List<File> fileList = githubSearchService.searchOnGithub(new File("", "", ""));

		assertEquals(fileList.size(), 10);
	}

}
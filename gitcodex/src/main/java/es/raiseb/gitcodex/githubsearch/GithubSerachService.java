package es.raiseb.gitcodex.githubsearch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.raiseb.gitcodex.file.File;

@Service
public class GithubSerachService {

	@Transactional
	public List<File> searchOnGithub(File file) {
		List<File> mockFiles = new ArrayList<File>();

		mockFiles.add(new File("balancer.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/balancer.erl"));

		mockFiles.add(new File("client.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/client.erl"));

		mockFiles.add(
				new File("dicc.erl", "pollfic", "https://github.com/braismcastro/pollfic/blob/master/src/dicc.erl"));

		mockFiles.add(new File("discover.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/discover.erl"));

		mockFiles.add(new File("encrypt.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/encrypt.erl"));

		mockFiles.add(new File("filter.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/filter.erl"));

		mockFiles
				.add(new File("gui.erl", "pollfic", "https://github.com/braismcastro/pollfic/blob/master/src/gui.erl"));

		mockFiles.add(new File("mylist.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/mylist.erl"));

		mockFiles.add(new File("server.erl", "pollfic",
				"https://github.com/braismcastro/pollfic/blob/master/src/server.erl"));

		mockFiles.add(
				new File("util.erl", "pollfic", "https://github.com/braismcastro/pollfic/blob/master/src/util.erl"));

		return mockFiles;
	}

}

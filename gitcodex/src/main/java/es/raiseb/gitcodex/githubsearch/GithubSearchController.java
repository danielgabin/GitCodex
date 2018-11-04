package es.raiseb.gitcodex.githubsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.raiseb.gitcodex.file.File;

@Controller
public class GithubSearchController {

	@Autowired
	private GithubSerachService githubSearchService;

	@GetMapping("/githubsearch")
	public String githubSearch(Model model) {
		File mockFile = new File("", "", "");
		model.addAttribute("filePage", githubSearchService.searchOnGithub(mockFile));
		return "githubsearch/githubsearchresults";
	}

}

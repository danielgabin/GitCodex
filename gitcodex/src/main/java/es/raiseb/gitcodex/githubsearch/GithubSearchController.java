package es.raiseb.gitcodex.githubsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.raiseb.gitcodex.file.CodeFile;

@Controller
public class GithubSearchController {

	@Autowired
	private GithubSerachService githubSearchService;

	@GetMapping("/githubsearch")
	public String githubSearch(Model model) {
		CodeFile mockFile = new CodeFile("", "", "", "", "");
		model.addAttribute("filePage", githubSearchService.searchOnGithub(mockFile));
		return "githubsearch/githubsearchresults";
	}

}

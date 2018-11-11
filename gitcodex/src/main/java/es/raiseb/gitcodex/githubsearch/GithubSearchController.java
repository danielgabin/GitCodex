package es.raiseb.gitcodex.githubsearch;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import es.raiseb.gitcodex.file.CodeFile;

@Controller
public class GithubSearchController {

	@Autowired
	private GithubSerachService githubSearchService;

	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

	@RequestMapping("/")
	public String uploading(Model model) {
		File file = new File(uploadingdir);
		model.addAttribute("files", file.listFiles());
		return "githubsearch/home";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadingPost(Model model, @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles)
			throws IOException {
		for (MultipartFile uploadedFile : uploadingFiles) {
			model.addAttribute("filePage", githubSearchService.searchOnGithub(uploadedFile));
		}

		return "githubsearch/githubsearchresults";
	}

}

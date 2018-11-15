package es.raiseb.gitcodex.githubsearch;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
	private GithubSearchService githubSearchService;

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
			String codeFileContent = githubSearchService.parseMultipartFile(uploadedFile);
			model.addAttribute("filePage", githubSearchService.searchOnGithub(codeFileContent));
			model.addAttribute("searchFile", codeFileContent);
		}
		return "githubsearch/githubsearchresults";
	}

	@RequestMapping(value = "/fileReview", method = RequestMethod.POST)
	public String fileReview(Model model, @RequestParam("searchedFile") String searchedFile,
			@RequestParam String foundFileId) throws IOException {
		CodeFile foundFile = githubSearchService.findById(foundFileId);
		String[] foundFileLines = foundFile.getFile_content().split("\n");

		model.addAttribute("file", foundFile);
		model.addAttribute("resultMap", githubSearchService.compareFiles(searchedFile, foundFileLines));
		model.addAttribute("foundFileLinesList", Arrays.asList(foundFileLines));

		return "githubsearch/fileReview";
	}

}

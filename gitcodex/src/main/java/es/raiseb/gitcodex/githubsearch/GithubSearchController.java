package es.raiseb.gitcodex.githubsearch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

	private Map<HttpSession, String> sessionMap = new HashMap<HttpSession, String>();

	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

	@RequestMapping("/")
	public String uploading(Model model) {
		File file = new File(uploadingdir);
		model.addAttribute("files", file.listFiles());
		return "githubsearch/home";
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public String uploadingPost(Model model, @RequestParam("uploadingFiles") MultipartFile[] uploadingFiles,
			HttpSession session, @RequestParam int page) throws IOException {
		if (uploadingFiles.length > 1) {
			List<String> fileContentList = new ArrayList<String>();
			for (MultipartFile uploadedFile : uploadingFiles) {
				String codeFileContent = githubSearchService.parseMultipartFile(uploadedFile);
				fileContentList.add(codeFileContent);
			}
			model.addAttribute("repositoryResult", githubSearchService.searchProjects(fileContentList));
			return "githubsearch/githubsearchrepositoryresults";
		} else {
			model.addAttribute("pIni", 1);
			model.addAttribute("pFin", 5);
			for (MultipartFile uploadedFile : uploadingFiles) {
				String codeFileContent = githubSearchService.parseMultipartFile(uploadedFile);
				model.addAttribute("filePage", githubSearchService.searchOnGithub(codeFileContent, page));
				model.addAttribute("searchFile", codeFileContent);
				sessionMap.put(session, codeFileContent);
			}
			return "githubsearch/githubsearchresults";
		}
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.GET)
	public String uploadingGet(Model model, HttpSession session, @RequestParam int page) throws IOException {
		String codeFileContent = sessionMap.get(session);
		if (codeFileContent != null) {
			Page<CodeFile> filePage = githubSearchService.searchOnGithub(codeFileContent, page);
			int totalPages = filePage.getTotalPages();
			model.addAttribute("filePage", filePage);
			model.addAttribute("searchFile", codeFileContent);
			int pIni = 0;
			int pFin = 0;
			if (page < 2) {
				pIni = 1;
				pFin = 5;
			} else if (page > totalPages - 2) {
				pIni = totalPages - 6;
				pFin = totalPages;
			} else {
				pIni = page - 2;
				pFin = page + 2;
			}
			model.addAttribute("pIni", pIni);
			model.addAttribute("pFin", pFin);
			return "githubsearch/githubsearchresults";
		} else {
			return "error/general";
		}
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

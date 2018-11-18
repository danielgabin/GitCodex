package es.raiseb.gitcodex.githubsearch;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.raiseb.gitcodex.file.CodeFile;

@Service
public class GithubSearchService {

	@Autowired
	GithubElasticsearchRepository elasticRepository;

	@Autowired
	GithubSearchProjectRepository elasticProjectRepository;

	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

	public String parseMultipartFile(MultipartFile uploadedFile) throws IOException {
		File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
		uploadedFile.transferTo(file);
		ByteArrayInputStream stream = new ByteArrayInputStream(uploadedFile.getBytes());
		int n = stream.available();
		byte[] bytes = new byte[n];
		stream.read(bytes, 0, n);

		return new String(bytes, StandardCharsets.UTF_8);
	}

	public List<Boolean> compareFiles(String searchedFile, String[] foundFileLines) throws IOException {

		Boolean[] commonLinesMap = new Boolean[foundFileLines.length];

		for (int i = 0; i < foundFileLines.length; i++) {
			if (searchedFile.contains(foundFileLines[i])) {
				commonLinesMap[i] = true;
			} else {
				commonLinesMap[i] = false;
			}
		}

		return Arrays.asList(commonLinesMap);
	}

	public CodeFile findById(String id) {
		return elasticRepository.findById(id).get();
	}

	@Transactional
	public List<CodeFile> searchOnGithub(String uploadedFile) throws IOException {
		return elasticRepository.findByCodeFileContent(StringEscapeUtils.escapeJava(uploadedFile));
	}

	public List<String> searchProjects(List<String> uploadedFiles) throws IOException {
		String[] uploadedFilesArray = new String[uploadedFiles.size()];
		int i = 0;
		for (String uploadedFile : uploadedFiles) {
			uploadedFilesArray[i] = StringEscapeUtils.escapeJava(uploadedFile);
			i++;
		}

		return elasticProjectRepository.findProjects(uploadedFilesArray);
	}

}

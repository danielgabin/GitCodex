package es.raiseb.gitcodex.githubsearch;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import es.raiseb.gitcodex.file.CodeFile;

@Service
public class GithubSerachService {

	@Autowired
	GithubElasticsearchRepository elasticRepository;

	public static final String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

	@Transactional
	public List<CodeFile> searchOnGithub(MultipartFile uploadedFile) throws IOException {

		File file = new File(uploadingdir + uploadedFile.getOriginalFilename());
		uploadedFile.transferTo(file);
		ByteArrayInputStream stream = new ByteArrayInputStream(uploadedFile.getBytes());
		int n = stream.available();
		byte[] bytes = new byte[n];
		stream.read(bytes, 0, n);
		String codeFileContent = new String(bytes, StandardCharsets.UTF_8);

		return elasticRepository.findByCodeFileContent(StringEscapeUtils.escapeJava(codeFileContent));
	}

}

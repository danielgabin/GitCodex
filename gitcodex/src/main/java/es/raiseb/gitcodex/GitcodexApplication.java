package es.raiseb.gitcodex;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import es.raiseb.gitcodex.githubsearch.UploadingController;

@SpringBootApplication
public class GitcodexApplication {

	public static void main(String[] args) {
		new File(UploadingController.uploadingdir).mkdirs();
		SpringApplication.run(GitcodexApplication.class, args);
	}
}

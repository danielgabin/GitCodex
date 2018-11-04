package es.raiseb.gitcodex.file;

public class File {

	private String name;
	private String repositoryName;
	private String url;

	public File(String name, String repositoryName, String url) {
		super();
		this.name = name;
		this.repositoryName = repositoryName;
		this.url = url;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

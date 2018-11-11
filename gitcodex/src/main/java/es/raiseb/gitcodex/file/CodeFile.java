package es.raiseb.gitcodex.file;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "elasticfiles", type = "elasticfiles")
public class CodeFile {

	@Id
	private String id;

	@Field(type = FieldType.Text)
	private String file_name;

	@Field(type = FieldType.Text)
	private String file_url;

	@Field(type = FieldType.Text)
	private String repository_url;

	@Field(type = FieldType.Text)
	private String file_content;

	public CodeFile() {

	}

	public CodeFile(String id, String file_name, String file_url, String repository_url, String file_content) {
		super();
		this.id = id;
		this.file_name = file_name;
		this.file_url = file_url;
		this.repository_url = repository_url;
		this.file_content = file_content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getRepository_url() {
		return repository_url;
	}

	public void setRepository_url(String repository_url) {
		this.repository_url = repository_url;
	}

	public String getFile_content() {
		return file_content;
	}

	public void setFile_content(String file_content) {
		this.file_content = file_content;
	}

}
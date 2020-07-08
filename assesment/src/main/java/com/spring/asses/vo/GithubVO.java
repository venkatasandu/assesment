package com.spring.asses.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "total_count", "incomplete_results", "items", "fork","url","forks_url","keys_url","collaborators_url",
	"teams_url","" })
public class GithubVO {
	
	String html_url;
	String watchers_count;
	String language;
	String description;
	String name;
	
	
	
	public GithubVO() {
	}
	public GithubVO(String html_url, String watchers_count, String language, String description, String name) {
		super();
		this.html_url = html_url;
		this.watchers_count = watchers_count;
		this.language = language;
		this.description = description;
		this.name = name;
	}
	public String getHtml_url() {
		return html_url;
	}
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}
	public String getWatchers_count() {
		return watchers_count;
	}
	public void setWatchers_count(String watchers_count) {
		this.watchers_count = watchers_count;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return "GithubVO [html_url=" + html_url + ", watchers_count=" + watchers_count + ", language=" + language
				+ ", description=" + description + ", name=" + name + "]";
	}
	
	

}

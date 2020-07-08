package com.spring.asses.vo;

public class GithubUserVO {

	private String id;
	private String login;
	private String htmlUrl;
	
	public GithubUserVO() {
	}

	public GithubUserVO(String id, String login, String htmlUrl) {
		super();
		this.id = id;
		this.login = login;
		this.htmlUrl = htmlUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}

	@Override
	public String toString() {
		return "GithubUserVO [id=" + id + ", login=" + login + ", htmlUrl=" + htmlUrl + "]";
	}
	
	
}

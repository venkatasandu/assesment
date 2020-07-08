package com.spring.asses.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.asses.vo.GithubUserVO;
import com.spring.asses.vo.GithubVO;

@RestController
public class GithubUserController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${git.repositories.query.uri}")
	private String hottestRepositoriesUri;
	
	@Value("${git.users.query.uri}")
	private String userAccountsWithZeroFollowersUri;
	
	@GetMapping("/repositories/")
	public ResponseEntity<List<GithubVO>> hottestRepositories() 
			throws JsonMappingException, JsonProcessingException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/vnd.github.preview");
		
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		
		String datestr = formatDate();
		System.out.println("gitUri :" + hottestRepositoriesUri);
		System.out.println("datestr :" + datestr);

		HttpEntity<String> response = 
				restTemplate.exchange(hottestRepositoriesUri, 
						HttpMethod.GET, entity, String.class,datestr,"star","desc");

		String s = response.getBody();		
		
		return ResponseEntity.ok(readRepositories(s));		
	}
	
	@GetMapping("/users/")
	public ResponseEntity<List<GithubUserVO>> 
			userAccountsWithZeroFollowers(@RequestParam("count") Integer count) 
			throws JsonMappingException, JsonProcessingException {

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/vnd.github.preview");
		
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

		HttpEntity<String> response = 
				restTemplate.exchange(userAccountsWithZeroFollowersUri, 
						HttpMethod.GET, entity, String.class ,"star","asc");
		
		String s = response.getBody();		
		
		return ResponseEntity.ok(readUserObjects(s, count));		
	}	
	
	private String formatDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		c.add(Calendar.DATE, -7);
		Date date = c.getTime();
		return format.format(date);
	}
	
	private List<GithubVO> readRepositories(String json) {
		
		List<GithubVO> list = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		JsonNode items = root.path("items");
		Iterator<JsonNode> elements = items.elements();
		while(elements.hasNext()){
			JsonNode node = elements.next();
			
			String htmlUrl = node.path("html_url").asText();
			String watchersCount = node.path("watchers_count").asText();
			String language = node.path("language").asText();
			String description = node.path("description").asText();
			String name = node.path("name").asText();
			
			list.add(new GithubVO(htmlUrl,watchersCount,language,description,name));
		}
		return list;
	}
	
	private List<GithubUserVO> readUserObjects(String json, int count) {
		
		List<GithubUserVO> list = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = null;
		try {
			root = mapper.readTree(json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		int num=0;
		JsonNode items = root.path("items");
		Iterator<JsonNode> elements = items.elements();
		while(elements.hasNext() && num<count){
			++num;
			JsonNode node = elements.next();
			
			String htmlUrl = node.path("html_url").asText();
			String login = node.path("login").asText();
			String id = node.path("id").asText();
			
			list.add(new GithubUserVO(id,login,htmlUrl));
		}
		return list;
	}	
	
}

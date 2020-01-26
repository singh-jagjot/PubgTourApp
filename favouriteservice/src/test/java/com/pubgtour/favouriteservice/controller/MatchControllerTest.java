package com.pubgtour.favouriteservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubgtour.favouriteservice.model.Match;
import com.pubgtour.favouriteservice.services.MatchService;

@RunWith(SpringRunner.class)
@WebMvcTest(MatchController.class)
public class MatchControllerTest {

	public MatchControllerTest() {

	}

	@Autowired
	private transient MockMvc mvc;

	@MockBean
	private transient MatchService service;
	@InjectMocks
	private MatchController controller;

	private transient Match match;

	static List<Match> matches;

	String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0LXVzZXIiLCJpYXQiOjE1NTM5NDU5NDl9.5PgV1Jzl5gwv5HZdm5mz__18tz2EqSmPRwEv2x7m6rU";

	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		} catch (JsonProcessingException e) {
			result = "Json processing error";
		}
		return result;
	}

	@Before
	public void setUp() {
		matches = new ArrayList<>();
		match = new Match("0fc8b9c6-e9a6-49b9-a074-edslolz763e89rb777", "test-blue-title-id", "testMap", false,
				"test-seasonState", 145, "normal", "2019-03-18", "test-user", "test-comment");
		matches.add(match);
		match = new Match("0fc8b9c6-e9a6-49b9-a074-edsbflolz3e89rb777", "test-blue-title-id", "testMap", false,
				"test-seasonState", 145, "normal", "2019-03-18", "test-user", "test-comment");
		matches.add(match);
	}

	@Test
	public void testSaveNewMatchSuccess() throws Exception {
		System.out.println(this.service);
		when(this.service.saveMatch(match)).thenReturn(true);
		mvc.perform(post("/api/match").header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON).content(jsonToString(match))).andExpect(status().isCreated());
		verify(this.service, times(1)).saveMatch(Mockito.any(Match.class));
		verifyNoMoreInteractions(this.service);
	}

	@Test
	public void testUpdateMatchSuccess() throws Exception {

		match.setComments("not so good");
		when(this.service.updateMatch(match)).thenReturn(matches.get(0));
		mvc.perform(put("/api/match").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(match))).andExpect(status().isOk());
		verify(this.service, times(1)).updateMatch(Mockito.any(Match.class));
		verifyNoMoreInteractions(this.service);
	}

	@Test
	public void testDeleteMatchByUserNameAndMatchById() throws Exception {
		when(service.deleteByUserNameAndMatchId(match.getUserName(), match.getMatchId())).thenReturn(true);
		mvc.perform(delete("/api/match/{matchId}", "0fc8b9c6-e9a6-49b9-a074-edsbflolz3e89rb777").header("Authorization",
				"Bearer " + token)).andExpect(status().isOk());
		verify(service, times(1)).deleteByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		verifyNoMoreInteractions(service);
	}

	@Test
	public void testFetchMyMatches() throws Exception {
		when(service.getMyMatchList("test-user")).thenReturn(matches);
		mvc.perform(
				get("/api/match").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
		verify(service, times(1)).getMyMatchList("test-user");
		verifyNoMoreInteractions(service);
	}
}

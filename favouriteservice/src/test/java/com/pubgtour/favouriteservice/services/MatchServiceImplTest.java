package com.pubgtour.favouriteservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pubgtour.favouriteservice.exceptions.MatchAlreadyExistsException;
import com.pubgtour.favouriteservice.exceptions.MatchNotFoundException;
import com.pubgtour.favouriteservice.model.Match;
import com.pubgtour.favouriteservice.repository.MatchRepository;

public class MatchServiceImplTest {

	public MatchServiceImplTest() {

	}

	@Mock
	private transient MatchRepository repo;

	@Mock
	private transient Match match;

	@InjectMocks
	private transient MatchServiceImpl matchServiceImpl;

	transient Optional<Match> optional;

	@Before
	public void setupMock() {
		MockitoAnnotations.initMocks(this);
		match = new Match("0fc8b9c6-e9a6-49b9-a074-edsbfbg763e89rb777", "test-blue-title-id", "testMap", false,
				"test-seasonState", 145, "normal", "2019-03-18", "test-user", "test-comment");
	}

	@Test
	public void testMockCreation() {
		assertNotNull("jpaRepository creation fails: use @InjectMocks on matchServiceImpl", match);
	}

	@Test
	public void testSaveMatchSuccess() throws MatchAlreadyExistsException {
		when(repo.save(match)).thenReturn(match);
		final boolean flag = matchServiceImpl.saveMatch(match);
		assertTrue("Saving match failed", flag);
		verify(repo, times(1)).save(match);
	}

	@Test(expected = MatchAlreadyExistsException.class)
	public void testSaveMatchFailure() throws MatchAlreadyExistsException {
		when(repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId())).thenReturn(match);
		when(repo.save(match)).thenReturn(match);
		final boolean flag = matchServiceImpl.saveMatch(match);
		assertFalse("Saving match failed", flag);
		verify(repo, times(1)).findById(match.getId());
	}

	@Test
	public void testUpdateMatch() throws MatchNotFoundException {
		when(repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId())).thenReturn(match);
		when(repo.save(match)).thenReturn(match);
		match.setComments("not good");
		final Match newMatch = matchServiceImpl.updateMatch(match);
		assertEquals("not good", newMatch.getComments());
		verify(repo, times(1)).save(match);
		verify(repo, times(1)).findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
	}

	@Test
	public void testDeleteMatch() throws MatchNotFoundException {
		when(repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId())).thenReturn(match);
		doNothing().when(repo).delete(match);
		final boolean flag = matchServiceImpl.deleteByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		assertTrue("Deleting match failed", flag);
		verify(repo, times(1)).delete(match);
		verify(repo, times(1)).findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
	}

	@Test
	public void testGetMyMatchs() {
		List<Match> matchList = new ArrayList<>();
		matchList.add(match);
		when(repo.findByUserName("test-user")).thenReturn(matchList);
		List<Match> newMatchList = matchServiceImpl.getMyMatchList("test-user");
		assertEquals(matchList, newMatchList);
		verify(repo, times(1)).findByUserName("test-user");
	}
	
}

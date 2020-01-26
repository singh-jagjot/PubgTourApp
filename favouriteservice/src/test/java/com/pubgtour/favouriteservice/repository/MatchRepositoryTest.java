package com.pubgtour.favouriteservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pubgtour.favouriteservice.model.Match;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
public class MatchRepositoryTest {

	@Autowired
	private MatchRepository repo;
	private Match match;
	
	public MatchRepositoryTest() {
		
	}
	
	@Before
	public void setUp() throws Exception {
		match = new Match("0fc8b9c6-e9a6-49b9-a074-edslolz763e89rb777", "test-blue-title-id", "testMap", false,
				"test-seasonState", 145, "normal", "2019-03-18", "test-user", "test-comment");
		repo.save(match);
	}
	
	@Test
	public void testSaveMatch() throws Exception {
		Match newMatch =  repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		assertThat(newMatch.equals(match));
	}
	@Test
	public void testFindByName() throws Exception {
		List<Match> myMatches = repo.findByUserName(match.getUserName());
		assertThat(myMatches.get(0).equals(match));
	}
	@Test
	public void testUpdateMovie() throws Exception {
		match.setComments("Another test-comment");
		repo.save(match);
		Match newMatch =  repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		assertThat(newMatch.getComments()).isEqualTo("Another test-comment");
	}
	
	@Test
	public void testDeleteMovie() throws Exception {
		Match newMatch = repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		assertThat(newMatch.getMatchId()).isEqualTo("0fc8b9c6-e9a6-49b9-a074-edslolz763e89rb777");
		repo.delete(newMatch);
		assertThat(repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId())).isEqualTo(null);
	}
	
	@After
	public void cleanUp() throws Exception {
		repo.deleteAllInBatch();
	}
}

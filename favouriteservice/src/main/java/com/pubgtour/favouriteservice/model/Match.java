package com.pubgtour.favouriteservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pubgMatches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String matchId;
	private String titleId;
	private String mapName;
	private Boolean isCustomMatch;
	private String seasonState;
	private Integer duration;
	private String gameMode;
	private String createdAt;
	private String userName;
	private String comments;
	
	public Match() {}

	public Match(String matchId, String titleId, String mapName, Boolean isCustomMatch, String seasonState,
			Integer duration, String gameMode, String createdAt, String userName, String comments) {
		super();
		this.matchId = matchId;
		this.titleId = titleId;
		this.mapName = mapName;
		this.isCustomMatch = isCustomMatch;
		this.seasonState = seasonState;
		this.duration = duration;
		this.gameMode = gameMode;
		this.createdAt = createdAt;
		this.userName = userName;
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public String getMapName() {
		return mapName;
	}

	public void setMapName(String mapName) {
		this.mapName = mapName;
	}

	public Boolean getIsCustomMatch() {
		return isCustomMatch;
	}

	public void setIsCustomMatch(Boolean isCustomMatch) {
		this.isCustomMatch = isCustomMatch;
	}

	public String getSeasonState() {
		return seasonState;
	}

	public void setSeasonState(String seasonState) {
		this.seasonState = seasonState;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Match [id=" + id + ", matchId=" + matchId + ", titleId=" + titleId + ", mapName=" + mapName
				+ ", isCustomMatch=" + isCustomMatch + ", seasonState=" + seasonState + ", duration=" + duration
				+ ", gameMode=" + gameMode + ", createdAt=" + createdAt + ", userName=" + userName + ", comments="
				+ comments + "]";
	}
	
}

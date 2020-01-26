import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { AuthenticationService } from '../authentication/authentication.service';

@Injectable({
  providedIn: 'root'
})
export class PubgTournamentsService {

  pubgTournamentsEndPoint: string = "https://api.pubg.com/tournaments";
  pubgMatchEndPoint: string = "https://api.pubg.com/shards/tournament/matches";
  backendServerEndPoint: string = "http://localhost:8080/api/match";
  pubgApiHeaders: HttpHeaders = new HttpHeaders({
    "Accept": "application/vnd.api+json",
    "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiNDZkYzMyMC0zMmFlLTAxMzctNWM4Mi01ZDdjYjNkY2FkOGIiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTUzNjg0OTY1LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6InB1YmctdG91ciJ9.0h25LBjDy0W6hrSO7Vo6Q_96zd4iL5FsS1tUYDc-yeo"
  });

  constructor(private http: HttpClient, private authService: AuthenticationService) {
  }

  getBackendServerHeaders() {
    return {
      headers: {
        "Authorization": "Bearer " + this.authService.getToken()
      }
    };
  }

  pickTournaments(response) {
    return response["data"];
  }

  pickMatches(response) {
    return response["included"];
  }

  pickMatchSummary(response) {
    return response["data"];
  }
  getTournaments() {
    return this.http.get(this.pubgTournamentsEndPoint, {
      headers: this.pubgApiHeaders
    }).pipe(map(this.pickTournaments));
  }

  getMatches(tournamentId: string) {
    return this.http.get(this.pubgTournamentsEndPoint + "/" + tournamentId, {
      headers: this.pubgApiHeaders
    }).pipe(map(this.pickMatches));
  }

  getMatchInfo(matchId: string) {
    return this.http.get(this.pubgMatchEndPoint + "/" + matchId, {
      headers: this.pubgApiHeaders
    }).pipe(map(this.pickMatchSummary));
  }

  addToFavourities(match) {
    return this.http.post(this.backendServerEndPoint, match, this.getBackendServerHeaders());
  }

  getFavourities() {
    return this.http.get(this.backendServerEndPoint, this.getBackendServerHeaders());
  }

  updateFavourities(match) {
    return this.http.put(this.backendServerEndPoint, match, this.getBackendServerHeaders());
  }

  deleteFromFavourities(matchId) {
    return this.http.delete(this.backendServerEndPoint + "/" + matchId, this.getBackendServerHeaders());
  }
}

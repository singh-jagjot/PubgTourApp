import { Component, OnInit } from '@angular/core';
import { PubgDetailCard } from '../../pubg-detail-card';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.css']
})
export class MatchesComponent implements OnInit {
  tournamentId: string;
  matchCards: Array<PubgDetailCard> = [];
  error: string;
  constructor(private tournamentService: PubgTournamentsService, private route: ActivatedRoute) {
    this.tournamentId = this.route.snapshot.paramMap.get("tournamentId");
   }

  ngOnInit() {
    this.tournamentService.getMatches(this.tournamentId).subscribe((data) => {
      this.matchCards = data;
    },
    error => {
      this.error = error.status;
    });
  }

}

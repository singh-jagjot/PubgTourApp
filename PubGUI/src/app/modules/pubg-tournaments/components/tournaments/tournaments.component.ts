import { Component, OnInit } from '@angular/core';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { PubgDetailCard } from '../../pubg-detail-card';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.css']
})
export class TournamentsComponent implements OnInit {
  tournamentCards: Array<PubgDetailCard> = [];
  error: string;
  constructor(private tournamentService: PubgTournamentsService) { }
  ngOnInit() {
    this.tournamentService.getTournaments().subscribe((data) => {
      this.tournamentCards = data;
    },
    error => {
      this.error = error.status;
    });
  }
  
}

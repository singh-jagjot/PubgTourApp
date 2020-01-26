import { Component, OnInit } from '@angular/core';
import { PubgDetailCard } from '../../pubg-detail-card';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-favourities',
  templateUrl: './favourities.component.html',
  styleUrls: ['./favourities.component.css']
})
export class FavouritiesComponent implements OnInit {

  matchCards: Array<PubgDetailCard> = [];
  constructor(private tournamentService: PubgTournamentsService, private snackbar: MatSnackBar) { }

  ngOnInit() {
    this.tournamentService.getFavourities().subscribe((data: any) => {
      this.matchCards = data;
    });
  }

  deleteFromFavourities(match) {
    this.matchCards.splice(this.matchCards.indexOf(match), 1);
    this.tournamentService.deleteFromFavourities(match.matchId).subscribe(() => {
      this.snackbar.open("Match Deleted Successfully!", "", {
        duration: 3000
      })
    })
  }

}

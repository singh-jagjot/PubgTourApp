import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { PubgDetailCard } from '../../pubg-detail-card';
import { Router, ActivatedRoute } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { MatchSummaryDialogComponent } from '../match-summary-dialog/match-summary-dialog.component';
import { AuthenticationService } from 'src/app/modules/authentication/authentication.service';
import { FavouritiesMatchSummaryDialogComponent } from '../favourities-match-summary-dialog/favourities-match-summary-dialog.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'pubg-tournaments-detail-card',
  templateUrl: './detail-card.component.html',
  styleUrls: ['./detail-card.component.css']
})
export class DetailCardComponent implements OnInit {

  @Input()
  detailCard: any;
  @Input()
  isTournament: boolean;
  @Input()
  isMatch: boolean;
  @Input()
  isFavourities: boolean;
  @Output()
  deleteMatch = new EventEmitter();

  constructor(private router: Router, private route: ActivatedRoute, private authService: AuthenticationService,
    private dialog: MatDialog, private tournamentService: PubgTournamentsService, private snackBar: MatSnackBar) { }

  ngOnInit() {
  }

  viewMatches() {
    this.router.navigate([this.detailCard.id], { relativeTo: this.route });
  }

  getMatchSummary() {
    if (this.authService.isTokenExpired()) {
      this.router.navigate(["/login"]);
    }
    else {
      this.tournamentService.getMatchInfo(this.detailCard.id).subscribe((data) => {
        this.viewMatchDetail(data);
      },
      error => {
        this.snackBar.open("Network or Internal Server Error", "", {
          duration: 3000
        });
      });
    }
  }
  viewMatchDetail(data) {
    const dialogRef = this.dialog.open(MatchSummaryDialogComponent, {
      width: "370px",
      data: { matchSummary: data }
    });
    dialogRef.afterClosed().subscribe();
  }
  viewFavouritiesMatchSummary() {
    const dialogRef = this.dialog.open(FavouritiesMatchSummaryDialogComponent, {
      width: "400px",
      data: { favouritiesDetailCard: this.detailCard }
    });
    dialogRef.afterClosed().subscribe();
  }
  deleteFromFavourities() {
    this.deleteMatch.emit(this.detailCard);
  }
}

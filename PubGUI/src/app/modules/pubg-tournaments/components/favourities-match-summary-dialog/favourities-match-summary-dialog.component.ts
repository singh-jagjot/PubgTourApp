import { Component, OnInit, Inject } from '@angular/core';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-favourities-match-summary-dialog',
  templateUrl: './favourities-match-summary-dialog.component.html',
  styleUrls: ['./favourities-match-summary-dialog.component.css']
})
export class FavouritiesMatchSummaryDialogComponent implements OnInit {

  match: any;
  constructor(private tournamentService: PubgTournamentsService, private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<FavouritiesMatchSummaryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any){
      this.match = data.favouritiesDetailCard;
    }

  ngOnInit() {
  }

  onClose() {
    this.dialogRef.close();
  }

  updateFavourities() {
    this.tournamentService.updateFavourities(this.match).subscribe(() => {
      this.snackBar.open("Match Updated Successfully", "", {
        duration: 3000
      });
    },
    error => {
      this.snackBar.open("Network or Internal Server Error", "", {
        duration: 3000
      });
    });
  }
}

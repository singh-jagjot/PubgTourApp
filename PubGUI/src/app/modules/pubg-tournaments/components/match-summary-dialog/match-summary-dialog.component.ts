import { Component, OnInit, Inject } from '@angular/core';
import { PubgTournamentsService } from '../../pubg-tournaments.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-match-summary',
  templateUrl: './match-summary-dialog.component.html',
  styleUrls: ['./match-summary-dialog.component.css']
})
export class MatchSummaryDialogComponent implements OnInit {

  match: any;
  constructor(private tournamentService: PubgTournamentsService, private snackBar: MatSnackBar,
    private dialogRef: MatDialogRef<MatchSummaryDialogComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any) { 
      this.match = data.matchSummary.attributes;
      this.match.matchId = data.matchSummary.id;
    }

  ngOnInit() { }

  onClose() {
    this.dialogRef.close();
  }

  addToFavourities() {
    this.tournamentService.addToFavourities(this.match).subscribe(() => {
      this.snackBar.open("Match has been added to your Favourities", "",{
        duration: 3000
      });
      this.dialogRef.close();
    },
    error => {
      if (error.status == 409){
        this.snackBar.open("Match Already Exists In Favourities", "", {
          duration: 3000
        });
      }
      else{
        this.snackBar.open("Network or Internal Server Error", "", {
          duration: 3000
        });
      }
    });
  }
}

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PubgTournamentsRoutingModule } from './pubg-tournaments-routing.module';
import { TournamentsComponent } from './components/tournaments/tournaments.component';
import { HttpClientModule } from '@angular/common/http';
import { FavouritiesComponent } from './components/favourities/favourities.component';
import { MatchesComponent } from './components/matches/matches.component';
import { DetailCardComponent } from './components/detail-card/detail-card.component';
import { MatchSummaryDialogComponent } from './components/match-summary-dialog/match-summary-dialog.component';
import { SharedMaterialModule } from '../shared-material/shared-material.module';
import { FavouritiesMatchSummaryDialogComponent } from './components/favourities-match-summary-dialog/favourities-match-summary-dialog.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    TournamentsComponent,
    FavouritiesComponent,
    MatchesComponent,
    DetailCardComponent,
    MatchSummaryDialogComponent,
    FavouritiesMatchSummaryDialogComponent],
  imports: [
    CommonModule,
    PubgTournamentsRoutingModule,
    HttpClientModule,
    FormsModule,
    SharedMaterialModule
  ],
  entryComponents: [
    MatchSummaryDialogComponent,
    FavouritiesMatchSummaryDialogComponent
  ]
})
export class PubgTournamentsModule { }

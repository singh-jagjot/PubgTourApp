import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TournamentsComponent } from './components/tournaments/tournaments.component';
import { FavouritiesComponent } from './components/favourities/favourities.component';
import { MatchesComponent } from './components/matches/matches.component';
import { AuthGuardService } from '../authentication/auth-guard.service';

const routes: Routes = [
  {
    path: "home", children: [
      { path: "", redirectTo: "tournaments", pathMatch: "full" },
      { path: "tournaments", component: TournamentsComponent },
      { path: "tournaments/:tournamentId", component: MatchesComponent},
      { path: "favourities", component: FavouritiesComponent, canActivate: [AuthGuardService] }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PubgTournamentsRoutingModule { }

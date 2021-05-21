import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ResultatEcoleComponent } from '../list/resultat-ecole.component';
import { ResultatEcoleDetailComponent } from '../detail/resultat-ecole-detail.component';
import { ResultatEcoleUpdateComponent } from '../update/resultat-ecole-update.component';
import { ResultatEcoleRoutingResolveService } from './resultat-ecole-routing-resolve.service';

const resultatEcoleRoute: Routes = [
  {
    path: '',
    component: ResultatEcoleComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ResultatEcoleDetailComponent,
    resolve: {
      resultatEcole: ResultatEcoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ResultatEcoleUpdateComponent,
    resolve: {
      resultatEcole: ResultatEcoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ResultatEcoleUpdateComponent,
    resolve: {
      resultatEcole: ResultatEcoleRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(resultatEcoleRoute)],
  exports: [RouterModule],
})
export class ResultatEcoleRoutingModule {}

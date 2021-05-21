import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AuteurComponent } from '../list/auteur.component';
import { AuteurDetailComponent } from '../detail/auteur-detail.component';
import { AuteurUpdateComponent } from '../update/auteur-update.component';
import { AuteurRoutingResolveService } from './auteur-routing-resolve.service';

const auteurRoute: Routes = [
  {
    path: '',
    component: AuteurComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AuteurDetailComponent,
    resolve: {
      auteur: AuteurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AuteurUpdateComponent,
    resolve: {
      auteur: AuteurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AuteurUpdateComponent,
    resolve: {
      auteur: AuteurRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(auteurRoute)],
  exports: [RouterModule],
})
export class AuteurRoutingModule {}

import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ConteComponent } from '../list/conte.component';
import { ConteDetailComponent } from '../detail/conte-detail.component';
import { ConteUpdateComponent } from '../update/conte-update.component';
import { ConteRoutingResolveService } from './conte-routing-resolve.service';

const conteRoute: Routes = [
  {
    path: '',
    component: ConteComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ConteDetailComponent,
    resolve: {
      conte: ConteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ConteUpdateComponent,
    resolve: {
      conte: ConteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ConteUpdateComponent,
    resolve: {
      conte: ConteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(conteRoute)],
  exports: [RouterModule],
})
export class ConteRoutingModule {}

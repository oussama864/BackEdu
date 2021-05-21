import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EcolierComponent } from '../list/ecolier.component';
import { EcolierDetailComponent } from '../detail/ecolier-detail.component';
import { EcolierUpdateComponent } from '../update/ecolier-update.component';
import { EcolierRoutingResolveService } from './ecolier-routing-resolve.service';

const ecolierRoute: Routes = [
  {
    path: '',
    component: EcolierComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EcolierDetailComponent,
    resolve: {
      ecolier: EcolierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EcolierUpdateComponent,
    resolve: {
      ecolier: EcolierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EcolierUpdateComponent,
    resolve: {
      ecolier: EcolierRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(ecolierRoute)],
  exports: [RouterModule],
})
export class EcolierRoutingModule {}

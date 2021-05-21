import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { QcmRComponent } from '../list/qcm-r.component';
import { QcmRDetailComponent } from '../detail/qcm-r-detail.component';
import { QcmRUpdateComponent } from '../update/qcm-r-update.component';
import { QcmRRoutingResolveService } from './qcm-r-routing-resolve.service';

const qcmRRoute: Routes = [
  {
    path: '',
    component: QcmRComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QcmRDetailComponent,
    resolve: {
      qcmR: QcmRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QcmRUpdateComponent,
    resolve: {
      qcmR: QcmRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QcmRUpdateComponent,
    resolve: {
      qcmR: QcmRRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(qcmRRoute)],
  exports: [RouterModule],
})
export class QcmRRoutingModule {}

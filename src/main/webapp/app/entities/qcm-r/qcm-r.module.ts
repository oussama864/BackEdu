import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { QcmRComponent } from './list/qcm-r.component';
import { QcmRDetailComponent } from './detail/qcm-r-detail.component';
import { QcmRUpdateComponent } from './update/qcm-r-update.component';
import { QcmRDeleteDialogComponent } from './delete/qcm-r-delete-dialog.component';
import { QcmRRoutingModule } from './route/qcm-r-routing.module';

@NgModule({
  imports: [SharedModule, QcmRRoutingModule],
  declarations: [QcmRComponent, QcmRDetailComponent, QcmRUpdateComponent, QcmRDeleteDialogComponent],
  entryComponents: [QcmRDeleteDialogComponent],
})
export class QcmRModule {}

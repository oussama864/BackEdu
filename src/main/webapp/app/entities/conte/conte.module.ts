import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ConteComponent } from './list/conte.component';
import { ConteDetailComponent } from './detail/conte-detail.component';
import { ConteUpdateComponent } from './update/conte-update.component';
import { ConteDeleteDialogComponent } from './delete/conte-delete-dialog.component';
import { ConteRoutingModule } from './route/conte-routing.module';

@NgModule({
  imports: [SharedModule, ConteRoutingModule],
  declarations: [ConteComponent, ConteDetailComponent, ConteUpdateComponent, ConteDeleteDialogComponent],
  entryComponents: [ConteDeleteDialogComponent],
})
export class ConteModule {}

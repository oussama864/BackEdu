import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { EcolierComponent } from './list/ecolier.component';
import { EcolierDetailComponent } from './detail/ecolier-detail.component';
import { EcolierUpdateComponent } from './update/ecolier-update.component';
import { EcolierDeleteDialogComponent } from './delete/ecolier-delete-dialog.component';
import { EcolierRoutingModule } from './route/ecolier-routing.module';

@NgModule({
  imports: [SharedModule, EcolierRoutingModule],
  declarations: [EcolierComponent, EcolierDetailComponent, EcolierUpdateComponent, EcolierDeleteDialogComponent],
  entryComponents: [EcolierDeleteDialogComponent],
})
export class EcolierModule {}

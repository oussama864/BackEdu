import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AuteurComponent } from './list/auteur.component';
import { AuteurDetailComponent } from './detail/auteur-detail.component';
import { AuteurUpdateComponent } from './update/auteur-update.component';
import { AuteurDeleteDialogComponent } from './delete/auteur-delete-dialog.component';
import { AuteurRoutingModule } from './route/auteur-routing.module';

@NgModule({
  imports: [SharedModule, AuteurRoutingModule],
  declarations: [AuteurComponent, AuteurDetailComponent, AuteurUpdateComponent, AuteurDeleteDialogComponent],
  entryComponents: [AuteurDeleteDialogComponent],
})
export class AuteurModule {}

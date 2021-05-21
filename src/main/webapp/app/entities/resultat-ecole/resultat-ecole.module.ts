import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { ResultatEcoleComponent } from './list/resultat-ecole.component';
import { ResultatEcoleDetailComponent } from './detail/resultat-ecole-detail.component';
import { ResultatEcoleUpdateComponent } from './update/resultat-ecole-update.component';
import { ResultatEcoleDeleteDialogComponent } from './delete/resultat-ecole-delete-dialog.component';
import { ResultatEcoleRoutingModule } from './route/resultat-ecole-routing.module';

@NgModule({
  imports: [SharedModule, ResultatEcoleRoutingModule],
  declarations: [ResultatEcoleComponent, ResultatEcoleDetailComponent, ResultatEcoleUpdateComponent, ResultatEcoleDeleteDialogComponent],
  entryComponents: [ResultatEcoleDeleteDialogComponent],
})
export class ResultatEcoleModule {}

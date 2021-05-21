import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'auteur',
        data: { pageTitle: 'edu1App.auteur.home.title' },
        loadChildren: () => import('./auteur/auteur.module').then(m => m.AuteurModule),
      },
      {
        path: 'ecolier',
        data: { pageTitle: 'edu1App.ecolier.home.title' },
        loadChildren: () => import('./ecolier/ecolier.module').then(m => m.EcolierModule),
      },
      {
        path: 'ecole',
        data: { pageTitle: 'edu1App.ecole.home.title' },
        loadChildren: () => import('./ecole/ecole.module').then(m => m.EcoleModule),
      },
      {
        path: 'conte',
        data: { pageTitle: 'edu1App.conte.home.title' },
        loadChildren: () => import('./conte/conte.module').then(m => m.ConteModule),
      },
      {
        path: 'competition',
        data: { pageTitle: 'edu1App.competition.home.title' },
        loadChildren: () => import('./competition/competition.module').then(m => m.CompetitionModule),
      },
      {
        path: 'reponse',
        data: { pageTitle: 'edu1App.reponse.home.title' },
        loadChildren: () => import('./reponse/reponse.module').then(m => m.ReponseModule),
      },
      {
        path: 'resultat-ecole',
        data: { pageTitle: 'edu1App.resultatEcole.home.title' },
        loadChildren: () => import('./resultat-ecole/resultat-ecole.module').then(m => m.ResultatEcoleModule),
      },
      {
        path: 'participant',
        data: { pageTitle: 'edu1App.participant.home.title' },
        loadChildren: () => import('./participant/participant.module').then(m => m.ParticipantModule),
      },
      {
        path: 'localisation',
        data: { pageTitle: 'edu1App.localisation.home.title' },
        loadChildren: () => import('./localisation/localisation.module').then(m => m.LocalisationModule),
      },
      {
        path: 'qcm',
        data: { pageTitle: 'edu1App.qcm.home.title' },
        loadChildren: () => import('./qcm/qcm.module').then(m => m.QcmModule),
      },
      {
        path: 'qcm-r',
        data: { pageTitle: 'edu1App.qcmR.home.title' },
        loadChildren: () => import('./qcm-r/qcm-r.module').then(m => m.QcmRModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}

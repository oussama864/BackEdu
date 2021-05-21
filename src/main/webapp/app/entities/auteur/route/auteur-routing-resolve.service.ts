import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAuteur, Auteur } from '../auteur.model';
import { AuteurService } from '../service/auteur.service';

@Injectable({ providedIn: 'root' })
export class AuteurRoutingResolveService implements Resolve<IAuteur> {
  constructor(protected service: AuteurService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAuteur> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((auteur: HttpResponse<Auteur>) => {
          if (auteur.body) {
            return of(auteur.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Auteur());
  }
}

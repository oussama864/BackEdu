import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IResultatEcole, ResultatEcole } from '../resultat-ecole.model';
import { ResultatEcoleService } from '../service/resultat-ecole.service';

@Injectable({ providedIn: 'root' })
export class ResultatEcoleRoutingResolveService implements Resolve<IResultatEcole> {
  constructor(protected service: ResultatEcoleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IResultatEcole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((resultatEcole: HttpResponse<ResultatEcole>) => {
          if (resultatEcole.body) {
            return of(resultatEcole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ResultatEcole());
  }
}

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEcole, Ecole } from '../ecole.model';
import { EcoleService } from '../service/ecole.service';

@Injectable({ providedIn: 'root' })
export class EcoleRoutingResolveService implements Resolve<IEcole> {
  constructor(protected service: EcoleService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEcole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ecole: HttpResponse<Ecole>) => {
          if (ecole.body) {
            return of(ecole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ecole());
  }
}

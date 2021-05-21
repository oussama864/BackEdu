import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEcolier, Ecolier } from '../ecolier.model';
import { EcolierService } from '../service/ecolier.service';

@Injectable({ providedIn: 'root' })
export class EcolierRoutingResolveService implements Resolve<IEcolier> {
  constructor(protected service: EcolierService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEcolier> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((ecolier: HttpResponse<Ecolier>) => {
          if (ecolier.body) {
            return of(ecolier.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Ecolier());
  }
}

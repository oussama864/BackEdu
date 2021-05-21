import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IConte, Conte } from '../conte.model';
import { ConteService } from '../service/conte.service';

@Injectable({ providedIn: 'root' })
export class ConteRoutingResolveService implements Resolve<IConte> {
  constructor(protected service: ConteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IConte> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((conte: HttpResponse<Conte>) => {
          if (conte.body) {
            return of(conte.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Conte());
  }
}

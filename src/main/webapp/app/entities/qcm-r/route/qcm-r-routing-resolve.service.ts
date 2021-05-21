import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IQcmR, QcmR } from '../qcm-r.model';
import { QcmRService } from '../service/qcm-r.service';

@Injectable({ providedIn: 'root' })
export class QcmRRoutingResolveService implements Resolve<IQcmR> {
  constructor(protected service: QcmRService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQcmR> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((qcmR: HttpResponse<QcmR>) => {
          if (qcmR.body) {
            return of(qcmR.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QcmR());
  }
}

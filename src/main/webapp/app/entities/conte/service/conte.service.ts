import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IConte, getConteIdentifier } from '../conte.model';

export type EntityResponseType = HttpResponse<IConte>;
export type EntityArrayResponseType = HttpResponse<IConte[]>;

@Injectable({ providedIn: 'root' })
export class ConteService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/contes');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(conte: IConte): Observable<EntityResponseType> {
    return this.http.post<IConte>(this.resourceUrl, conte, { observe: 'response' });
  }

  update(conte: IConte): Observable<EntityResponseType> {
    return this.http.put<IConte>(`${this.resourceUrl}/${getConteIdentifier(conte) as string}`, conte, { observe: 'response' });
  }

  partialUpdate(conte: IConte): Observable<EntityResponseType> {
    return this.http.patch<IConte>(`${this.resourceUrl}/${getConteIdentifier(conte) as string}`, conte, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IConte>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IConte[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addConteToCollectionIfMissing(conteCollection: IConte[], ...contesToCheck: (IConte | null | undefined)[]): IConte[] {
    const contes: IConte[] = contesToCheck.filter(isPresent);
    if (contes.length > 0) {
      const conteCollectionIdentifiers = conteCollection.map(conteItem => getConteIdentifier(conteItem)!);
      const contesToAdd = contes.filter(conteItem => {
        const conteIdentifier = getConteIdentifier(conteItem);
        if (conteIdentifier == null || conteCollectionIdentifiers.includes(conteIdentifier)) {
          return false;
        }
        conteCollectionIdentifiers.push(conteIdentifier);
        return true;
      });
      return [...contesToAdd, ...conteCollection];
    }
    return conteCollection;
  }
}

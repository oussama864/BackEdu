jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEcole, Ecole } from '../ecole.model';
import { EcoleService } from '../service/ecole.service';

import { EcoleRoutingResolveService } from './ecole-routing-resolve.service';

describe('Service Tests', () => {
  describe('Ecole routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EcoleRoutingResolveService;
    let service: EcoleService;
    let resultEcole: IEcole | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EcoleRoutingResolveService);
      service = TestBed.inject(EcoleService);
      resultEcole = undefined;
    });

    describe('resolve', () => {
      it('should return IEcole returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcole = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultEcole).toEqual({ id: 'ABC' });
      });

      it('should return new IEcole if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcole = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEcole).toEqual(new Ecole());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcole = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultEcole).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

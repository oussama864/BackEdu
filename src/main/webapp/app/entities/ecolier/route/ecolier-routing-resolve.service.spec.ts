jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IEcolier, Ecolier } from '../ecolier.model';
import { EcolierService } from '../service/ecolier.service';

import { EcolierRoutingResolveService } from './ecolier-routing-resolve.service';

describe('Service Tests', () => {
  describe('Ecolier routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: EcolierRoutingResolveService;
    let service: EcolierService;
    let resultEcolier: IEcolier | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(EcolierRoutingResolveService);
      service = TestBed.inject(EcolierService);
      resultEcolier = undefined;
    });

    describe('resolve', () => {
      it('should return IEcolier returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcolier = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultEcolier).toEqual({ id: 'ABC' });
      });

      it('should return new IEcolier if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcolier = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultEcolier).toEqual(new Ecolier());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultEcolier = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultEcolier).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

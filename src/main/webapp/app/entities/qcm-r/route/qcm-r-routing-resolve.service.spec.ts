jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IQcmR, QcmR } from '../qcm-r.model';
import { QcmRService } from '../service/qcm-r.service';

import { QcmRRoutingResolveService } from './qcm-r-routing-resolve.service';

describe('Service Tests', () => {
  describe('QcmR routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: QcmRRoutingResolveService;
    let service: QcmRService;
    let resultQcmR: IQcmR | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(QcmRRoutingResolveService);
      service = TestBed.inject(QcmRService);
      resultQcmR = undefined;
    });

    describe('resolve', () => {
      it('should return IQcmR returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultQcmR = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultQcmR).toEqual({ id: 'ABC' });
      });

      it('should return new IQcmR if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultQcmR = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultQcmR).toEqual(new QcmR());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultQcmR = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultQcmR).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

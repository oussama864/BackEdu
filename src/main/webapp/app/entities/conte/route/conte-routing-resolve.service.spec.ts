jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IConte, Conte } from '../conte.model';
import { ConteService } from '../service/conte.service';

import { ConteRoutingResolveService } from './conte-routing-resolve.service';

describe('Service Tests', () => {
  describe('Conte routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: ConteRoutingResolveService;
    let service: ConteService;
    let resultConte: IConte | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(ConteRoutingResolveService);
      service = TestBed.inject(ConteService);
      resultConte = undefined;
    });

    describe('resolve', () => {
      it('should return IConte returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultConte = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultConte).toEqual({ id: 'ABC' });
      });

      it('should return new IConte if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultConte = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultConte).toEqual(new Conte());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultConte = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultConte).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

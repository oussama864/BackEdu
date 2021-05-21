jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IAuteur, Auteur } from '../auteur.model';
import { AuteurService } from '../service/auteur.service';

import { AuteurRoutingResolveService } from './auteur-routing-resolve.service';

describe('Service Tests', () => {
  describe('Auteur routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: AuteurRoutingResolveService;
    let service: AuteurService;
    let resultAuteur: IAuteur | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(AuteurRoutingResolveService);
      service = TestBed.inject(AuteurService);
      resultAuteur = undefined;
    });

    describe('resolve', () => {
      it('should return IAuteur returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuteur = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultAuteur).toEqual({ id: 'ABC' });
      });

      it('should return new IAuteur if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuteur = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultAuteur).toEqual(new Auteur());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 'ABC' };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultAuteur = result;
        });

        // THEN
        expect(service.find).toBeCalledWith('ABC');
        expect(resultAuteur).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});

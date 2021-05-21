import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IConte, Conte } from '../conte.model';

import { ConteService } from './conte.service';

describe('Service Tests', () => {
  describe('Conte Service', () => {
    let service: ConteService;
    let httpMock: HttpTestingController;
    let elemDefault: IConte;
    let expectedResult: IConte | IConte[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ConteService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        nom: 'AAAAAAA',
        type: 'AAAAAAA',
        description: 'AAAAAAA',
        prix: 0,
        language: 'AAAAAAA',
        imageUrl: 'AAAAAAA',
        titre: 'AAAAAAA',
        nbPage: 0,
        maisonEdition: 'AAAAAAA',
        createdBy: 'AAAAAAA',
        createdDate: currentDate,
        deleted: false,
        deletedBy: 'AAAAAAA',
        deletedDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            createdDate: currentDate.format(DATE_FORMAT),
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find('ABC').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Conte', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            createdDate: currentDate.format(DATE_FORMAT),
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Conte()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Conte', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            nom: 'BBBBBB',
            type: 'BBBBBB',
            description: 'BBBBBB',
            prix: 1,
            language: 'BBBBBB',
            imageUrl: 'BBBBBB',
            titre: 'BBBBBB',
            nbPage: 1,
            maisonEdition: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Conte', () => {
        const patchObject = Object.assign(
          {
            description: 'BBBBBB',
            prix: 1,
            language: 'BBBBBB',
            imageUrl: 'BBBBBB',
            titre: 'BBBBBB',
            nbPage: 1,
            deleted: true,
            deletedBy: 'BBBBBB',
          },
          new Conte()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Conte', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            nom: 'BBBBBB',
            type: 'BBBBBB',
            description: 'BBBBBB',
            prix: 1,
            language: 'BBBBBB',
            imageUrl: 'BBBBBB',
            titre: 'BBBBBB',
            nbPage: 1,
            maisonEdition: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            createdDate: currentDate,
            deletedDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Conte', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addConteToCollectionIfMissing', () => {
        it('should add a Conte to an empty array', () => {
          const conte: IConte = { id: 'ABC' };
          expectedResult = service.addConteToCollectionIfMissing([], conte);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(conte);
        });

        it('should not add a Conte to an array that contains it', () => {
          const conte: IConte = { id: 'ABC' };
          const conteCollection: IConte[] = [
            {
              ...conte,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addConteToCollectionIfMissing(conteCollection, conte);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Conte to an array that doesn't contain it", () => {
          const conte: IConte = { id: 'ABC' };
          const conteCollection: IConte[] = [{ id: 'CBA' }];
          expectedResult = service.addConteToCollectionIfMissing(conteCollection, conte);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(conte);
        });

        it('should add only unique Conte to an array', () => {
          const conteArray: IConte[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Rustic Games' }];
          const conteCollection: IConte[] = [{ id: 'ABC' }];
          expectedResult = service.addConteToCollectionIfMissing(conteCollection, ...conteArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const conte: IConte = { id: 'ABC' };
          const conte2: IConte = { id: 'CBA' };
          expectedResult = service.addConteToCollectionIfMissing([], conte, conte2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(conte);
          expect(expectedResult).toContain(conte2);
        });

        it('should accept null and undefined values', () => {
          const conte: IConte = { id: 'ABC' };
          expectedResult = service.addConteToCollectionIfMissing([], null, conte, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(conte);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

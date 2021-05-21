import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAuteur, Auteur } from '../auteur.model';

import { AuteurService } from './auteur.service';

describe('Service Tests', () => {
  describe('Auteur Service', () => {
    let service: AuteurService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuteur;
    let expectedResult: IAuteur | IAuteur[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AuteurService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        firstName: 'AAAAAAA',
        lastName: 'AAAAAAA',
        email: 'AAAAAAA',
        password: 'AAAAAAA',
        refUser: 'AAAAAAA',
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

      it('should create a Auteur', () => {
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

        service.create(new Auteur()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Auteur', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            password: 'BBBBBB',
            refUser: 'BBBBBB',
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

      it('should partial update a Auteur', () => {
        const patchObject = Object.assign(
          {
            firstName: 'BBBBBB',
            email: 'BBBBBB',
            password: 'BBBBBB',
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
          },
          new Auteur()
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

      it('should return a list of Auteur', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            email: 'BBBBBB',
            password: 'BBBBBB',
            refUser: 'BBBBBB',
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

      it('should delete a Auteur', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAuteurToCollectionIfMissing', () => {
        it('should add a Auteur to an empty array', () => {
          const auteur: IAuteur = { id: 'ABC' };
          expectedResult = service.addAuteurToCollectionIfMissing([], auteur);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(auteur);
        });

        it('should not add a Auteur to an array that contains it', () => {
          const auteur: IAuteur = { id: 'ABC' };
          const auteurCollection: IAuteur[] = [
            {
              ...auteur,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addAuteurToCollectionIfMissing(auteurCollection, auteur);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Auteur to an array that doesn't contain it", () => {
          const auteur: IAuteur = { id: 'ABC' };
          const auteurCollection: IAuteur[] = [{ id: 'CBA' }];
          expectedResult = service.addAuteurToCollectionIfMissing(auteurCollection, auteur);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(auteur);
        });

        it('should add only unique Auteur to an array', () => {
          const auteurArray: IAuteur[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'Rustic task-force' }];
          const auteurCollection: IAuteur[] = [{ id: 'ABC' }];
          expectedResult = service.addAuteurToCollectionIfMissing(auteurCollection, ...auteurArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const auteur: IAuteur = { id: 'ABC' };
          const auteur2: IAuteur = { id: 'CBA' };
          expectedResult = service.addAuteurToCollectionIfMissing([], auteur, auteur2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(auteur);
          expect(expectedResult).toContain(auteur2);
        });

        it('should accept null and undefined values', () => {
          const auteur: IAuteur = { id: 'ABC' };
          expectedResult = service.addAuteurToCollectionIfMissing([], null, auteur, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(auteur);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

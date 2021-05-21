import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILocalisation, Localisation } from '../localisation.model';

import { LocalisationService } from './localisation.service';

describe('Service Tests', () => {
  describe('Localisation Service', () => {
    let service: LocalisationService;
    let httpMock: HttpTestingController;
    let elemDefault: ILocalisation;
    let expectedResult: ILocalisation | ILocalisation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(LocalisationService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 'AAAAAAA',
        lat: 0,
        lng: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find('ABC').subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Localisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Localisation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Localisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            lat: 1,
            lng: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Localisation', () => {
        const patchObject = Object.assign({}, new Localisation());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Localisation', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            lat: 1,
            lng: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Localisation', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addLocalisationToCollectionIfMissing', () => {
        it('should add a Localisation to an empty array', () => {
          const localisation: ILocalisation = { id: 'ABC' };
          expectedResult = service.addLocalisationToCollectionIfMissing([], localisation);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(localisation);
        });

        it('should not add a Localisation to an array that contains it', () => {
          const localisation: ILocalisation = { id: 'ABC' };
          const localisationCollection: ILocalisation[] = [
            {
              ...localisation,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addLocalisationToCollectionIfMissing(localisationCollection, localisation);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Localisation to an array that doesn't contain it", () => {
          const localisation: ILocalisation = { id: 'ABC' };
          const localisationCollection: ILocalisation[] = [{ id: 'CBA' }];
          expectedResult = service.addLocalisationToCollectionIfMissing(localisationCollection, localisation);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(localisation);
        });

        it('should add only unique Localisation to an array', () => {
          const localisationArray: ILocalisation[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'incentivize' }];
          const localisationCollection: ILocalisation[] = [{ id: 'ABC' }];
          expectedResult = service.addLocalisationToCollectionIfMissing(localisationCollection, ...localisationArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const localisation: ILocalisation = { id: 'ABC' };
          const localisation2: ILocalisation = { id: 'CBA' };
          expectedResult = service.addLocalisationToCollectionIfMissing([], localisation, localisation2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(localisation);
          expect(expectedResult).toContain(localisation2);
        });

        it('should accept null and undefined values', () => {
          const localisation: ILocalisation = { id: 'ABC' };
          expectedResult = service.addLocalisationToCollectionIfMissing([], null, localisation, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(localisation);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

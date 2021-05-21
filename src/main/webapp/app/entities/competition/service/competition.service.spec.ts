import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ICompetition, Competition } from '../competition.model';

import { CompetitionService } from './competition.service';

describe('Service Tests', () => {
  describe('Competition Service', () => {
    let service: CompetitionService;
    let httpMock: HttpTestingController;
    let elemDefault: ICompetition;
    let expectedResult: ICompetition | ICompetition[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CompetitionService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
        date: 'AAAAAAA',
        code: 0,
        score: 0,
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

      it('should create a Competition', () => {
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

        service.create(new Competition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            date: 'BBBBBB',
            code: 1,
            score: 1,
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

      it('should partial update a Competition', () => {
        const patchObject = Object.assign(
          {
            date: 'BBBBBB',
            score: 1,
            deletedDate: currentDate.format(DATE_FORMAT),
          },
          new Competition()
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

      it('should return a list of Competition', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
            date: 'BBBBBB',
            code: 1,
            score: 1,
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

      it('should delete a Competition', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCompetitionToCollectionIfMissing', () => {
        it('should add a Competition to an empty array', () => {
          const competition: ICompetition = { id: 'ABC' };
          expectedResult = service.addCompetitionToCollectionIfMissing([], competition);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(competition);
        });

        it('should not add a Competition to an array that contains it', () => {
          const competition: ICompetition = { id: 'ABC' };
          const competitionCollection: ICompetition[] = [
            {
              ...competition,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, competition);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Competition to an array that doesn't contain it", () => {
          const competition: ICompetition = { id: 'ABC' };
          const competitionCollection: ICompetition[] = [{ id: 'CBA' }];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, competition);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(competition);
        });

        it('should add only unique Competition to an array', () => {
          const competitionArray: ICompetition[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'logistical' }];
          const competitionCollection: ICompetition[] = [{ id: 'ABC' }];
          expectedResult = service.addCompetitionToCollectionIfMissing(competitionCollection, ...competitionArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const competition: ICompetition = { id: 'ABC' };
          const competition2: ICompetition = { id: 'CBA' };
          expectedResult = service.addCompetitionToCollectionIfMissing([], competition, competition2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(competition);
          expect(expectedResult).toContain(competition2);
        });

        it('should accept null and undefined values', () => {
          const competition: ICompetition = { id: 'ABC' };
          expectedResult = service.addCompetitionToCollectionIfMissing([], null, competition, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(competition);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

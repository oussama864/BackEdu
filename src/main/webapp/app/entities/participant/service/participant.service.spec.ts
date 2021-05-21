import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IParticipant, Participant } from '../participant.model';

import { ParticipantService } from './participant.service';

describe('Service Tests', () => {
  describe('Participant Service', () => {
    let service: ParticipantService;
    let httpMock: HttpTestingController;
    let elemDefault: IParticipant;
    let expectedResult: IParticipant | IParticipant[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(ParticipantService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 'AAAAAAA',
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

      it('should create a Participant', () => {
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

        service.create(new Participant()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Participant', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
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

      it('should partial update a Participant', () => {
        const patchObject = Object.assign(
          {
            createdBy: 'BBBBBB',
            createdDate: currentDate.format(DATE_FORMAT),
            deleted: true,
            deletedBy: 'BBBBBB',
          },
          new Participant()
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

      it('should return a list of Participant', () => {
        const returnedFromService = Object.assign(
          {
            id: 'BBBBBB',
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

      it('should delete a Participant', () => {
        service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addParticipantToCollectionIfMissing', () => {
        it('should add a Participant to an empty array', () => {
          const participant: IParticipant = { id: 'ABC' };
          expectedResult = service.addParticipantToCollectionIfMissing([], participant);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(participant);
        });

        it('should not add a Participant to an array that contains it', () => {
          const participant: IParticipant = { id: 'ABC' };
          const participantCollection: IParticipant[] = [
            {
              ...participant,
            },
            { id: 'CBA' },
          ];
          expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, participant);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Participant to an array that doesn't contain it", () => {
          const participant: IParticipant = { id: 'ABC' };
          const participantCollection: IParticipant[] = [{ id: 'CBA' }];
          expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, participant);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(participant);
        });

        it('should add only unique Participant to an array', () => {
          const participantArray: IParticipant[] = [{ id: 'ABC' }, { id: 'CBA' }, { id: 'b a' }];
          const participantCollection: IParticipant[] = [{ id: 'ABC' }];
          expectedResult = service.addParticipantToCollectionIfMissing(participantCollection, ...participantArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const participant: IParticipant = { id: 'ABC' };
          const participant2: IParticipant = { id: 'CBA' };
          expectedResult = service.addParticipantToCollectionIfMissing([], participant, participant2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(participant);
          expect(expectedResult).toContain(participant2);
        });

        it('should accept null and undefined values', () => {
          const participant: IParticipant = { id: 'ABC' };
          expectedResult = service.addParticipantToCollectionIfMissing([], null, participant, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(participant);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});

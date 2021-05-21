import * as dayjs from 'dayjs';
import { IConte } from 'app/entities/conte/conte.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IQcm {
  id?: string;
  question?: string | null;
  choixDispo?: string | null;
  choixCorrect?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  refcon?: IConte | null;
  competition?: ICompetition | null;
}

export class Qcm implements IQcm {
  constructor(
    public id?: string,
    public question?: string | null,
    public choixDispo?: string | null,
    public choixCorrect?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public refcon?: IConte | null,
    public competition?: ICompetition | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getQcmIdentifier(qcm: IQcm): string | undefined {
  return qcm.id;
}

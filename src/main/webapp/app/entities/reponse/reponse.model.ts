import * as dayjs from 'dayjs';
import { IQcmR } from 'app/entities/qcm-r/qcm-r.model';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IReponse {
  id?: string;
  score?: number | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  qcmRS?: IQcmR[] | null;
  refEcole?: IEcole | null;
  competition?: ICompetition | null;
}

export class Reponse implements IReponse {
  constructor(
    public id?: string,
    public score?: number | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public qcmRS?: IQcmR[] | null,
    public refEcole?: IEcole | null,
    public competition?: ICompetition | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getReponseIdentifier(reponse: IReponse): string | undefined {
  return reponse.id;
}

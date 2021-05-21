import * as dayjs from 'dayjs';
import { IEcole } from 'app/entities/ecole/ecole.model';
import { IParticipant } from 'app/entities/participant/participant.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IResultatEcole {
  id?: string;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  ecole?: IEcole | null;
  participants?: IParticipant[] | null;
  competition?: ICompetition | null;
}

export class ResultatEcole implements IResultatEcole {
  constructor(
    public id?: string,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public ecole?: IEcole | null,
    public participants?: IParticipant[] | null,
    public competition?: ICompetition | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getResultatEcoleIdentifier(resultatEcole: IResultatEcole): string | undefined {
  return resultatEcole.id;
}

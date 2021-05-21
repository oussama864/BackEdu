import * as dayjs from 'dayjs';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { IEcolier } from 'app/entities/ecolier/ecolier.model';
import { IParticipant } from 'app/entities/participant/participant.model';
import { IQcm } from 'app/entities/qcm/qcm.model';
import { IConte } from 'app/entities/conte/conte.model';
import { IResultatEcole } from 'app/entities/resultat-ecole/resultat-ecole.model';

export interface ICompetition {
  id?: string;
  date?: string | null;
  code?: number | null;
  score?: number | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  reponses?: IReponse[] | null;
  ecoliers?: IEcolier[] | null;
  participants?: IParticipant[] | null;
  qcms?: IQcm[] | null;
  contes?: IConte[] | null;
  resultatEcoles?: IResultatEcole[] | null;
}

export class Competition implements ICompetition {
  constructor(
    public id?: string,
    public date?: string | null,
    public code?: number | null,
    public score?: number | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public reponses?: IReponse[] | null,
    public ecoliers?: IEcolier[] | null,
    public participants?: IParticipant[] | null,
    public qcms?: IQcm[] | null,
    public contes?: IConte[] | null,
    public resultatEcoles?: IResultatEcole[] | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getCompetitionIdentifier(competition: ICompetition): string | undefined {
  return competition.id;
}

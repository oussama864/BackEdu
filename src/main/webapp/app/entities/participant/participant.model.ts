import * as dayjs from 'dayjs';
import { IEcolier } from 'app/entities/ecolier/ecolier.model';
import { IReponse } from 'app/entities/reponse/reponse.model';
import { IResultatEcole } from 'app/entities/resultat-ecole/resultat-ecole.model';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IParticipant {
  id?: string;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  ecolier?: IEcolier | null;
  reponse?: IReponse | null;
  resultatEcole?: IResultatEcole | null;
  competition?: ICompetition | null;
}

export class Participant implements IParticipant {
  constructor(
    public id?: string,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public ecolier?: IEcolier | null,
    public reponse?: IReponse | null,
    public resultatEcole?: IResultatEcole | null,
    public competition?: ICompetition | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getParticipantIdentifier(participant: IParticipant): string | undefined {
  return participant.id;
}

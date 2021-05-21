import * as dayjs from 'dayjs';
import { ICompetition } from 'app/entities/competition/competition.model';

export interface IEcolier {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  refUser?: string | null;
  age?: number | null;
  niveau?: string | null;
  ecole?: string | null;
  dateDeNaissance?: dayjs.Dayjs | null;
  nomParent?: string | null;
  password?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  competition?: ICompetition | null;
}

export class Ecolier implements IEcolier {
  constructor(
    public id?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public refUser?: string | null,
    public age?: number | null,
    public niveau?: string | null,
    public ecole?: string | null,
    public dateDeNaissance?: dayjs.Dayjs | null,
    public nomParent?: string | null,
    public password?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public competition?: ICompetition | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getEcolierIdentifier(ecolier: IEcolier): string | undefined {
  return ecolier.id;
}

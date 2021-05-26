import * as dayjs from 'dayjs';
import { IConte } from 'app/entities/conte/conte.model';

export interface IAuteur {
  id?: string;
  firstName?: string | null;
  lastName?: string | null;
  email?: string | null;
  password?: string | null;
  refUser?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  contes?: IConte[] | null;
  points?: number ;
}

export class Auteur implements IAuteur {
  constructor(
    public id?: string,
    public firstName?: string | null,
    public lastName?: string | null,
    public email?: string | null,
    public password?: string | null,
    public refUser?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public contes?: IConte[] | null,
    public points?:number

  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getAuteurIdentifier(auteur: IAuteur): string | undefined {
  return auteur.id;
}

import * as dayjs from 'dayjs';
import { ILocalisation } from 'app/entities/localisation/localisation.model';
import { IReponse } from 'app/entities/reponse/reponse.model';

export interface IEcole {
  id?: string;
  nom?: string | null;
  adresse?: string | null;
  email?: string | null;
  login?: string | null;
  password?: string | null;
  listeClasses?: string | null;
  createdBy?: string | null;
  createdDate?: dayjs.Dayjs | null;
  deleted?: boolean | null;
  deletedBy?: string | null;
  deletedDate?: dayjs.Dayjs | null;
  localisation?: ILocalisation | null;
  reponses?: IReponse[] | null;
}

export class Ecole implements IEcole {
  constructor(
    public id?: string,
    public nom?: string | null,
    public adresse?: string | null,
    public email?: string | null,
    public login?: string | null,
    public password?: string | null,
    public listeClasses?: string | null,
    public createdBy?: string | null,
    public createdDate?: dayjs.Dayjs | null,
    public deleted?: boolean | null,
    public deletedBy?: string | null,
    public deletedDate?: dayjs.Dayjs | null,
    public localisation?: ILocalisation | null,
    public reponses?: IReponse[] | null
  ) {
    this.deleted = this.deleted ?? false;
  }
}

export function getEcoleIdentifier(ecole: IEcole): string | undefined {
  return ecole.id;
}

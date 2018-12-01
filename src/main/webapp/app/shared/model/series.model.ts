import { ISeason } from 'app/shared/model//season.model';

export interface ISeries {
    id?: number;
    name?: string;
    releaseYear?: number;
    description?: string;
    seasons?: ISeason[];
}

export class Series implements ISeries {
    constructor(
        public id?: number,
        public name?: string,
        public releaseYear?: number,
        public description?: string,
        public seasons?: ISeason[]
    ) {}
}

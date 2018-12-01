import { Moment } from 'moment';
import { ISeason } from 'app/shared/model//season.model';

export interface IEpisode {
    id?: number;
    number?: number;
    title?: string;
    releaseDate?: Moment;
    duration?: number;
    season?: ISeason;
}

export class Episode implements IEpisode {
    constructor(
        public id?: number,
        public number?: number,
        public title?: string,
        public releaseDate?: Moment,
        public duration?: number,
        public season?: ISeason
    ) {}
}

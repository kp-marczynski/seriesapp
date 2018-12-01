import { ISeries } from 'app/shared/model//series.model';
import { IEpisode } from 'app/shared/model//episode.model';

export interface ISeason {
    id?: number;
    number?: number;
    description?: string;
    releaseYear?: number;
    series?: ISeries;
    episodes?: IEpisode[];
}

export class Season implements ISeason {
    constructor(
        public id?: number,
        public number?: number,
        public description?: string,
        public releaseYear?: number,
        public series?: ISeries,
        public episodes?: IEpisode[]
    ) {}
}

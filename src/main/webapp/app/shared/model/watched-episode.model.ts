import { IUser } from 'app/core/user/user.model';
import { IEpisode } from 'app/shared/model//episode.model';

export const enum Rate {
    BAD = 'BAD',
    MEDIOCRE = 'MEDIOCRE',
    AVERAGE = 'AVERAGE',
    GOOD = 'GOOD',
    AWESOME = 'AWESOME'
}

export interface IWatchedEpisode {
    id?: number;
    rate?: Rate;
    comment?: string;
    user?: IUser;
    episode?: IEpisode;
}

export class WatchedEpisode implements IWatchedEpisode {
    constructor(public id?: number, public rate?: Rate, public comment?: string, public user?: IUser, public episode?: IEpisode) {}
}

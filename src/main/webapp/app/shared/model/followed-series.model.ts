import { IUser } from 'app/core/user/user.model';
import { ISeries } from 'app/shared/model//series.model';

export const enum Rate {
    BAD = 'BAD',
    MEDIOCRE = 'MEDIOCRE',
    AVERAGE = 'AVERAGE',
    GOOD = 'GOOD',
    AWESOME = 'AWESOME'
}

export interface IFollowedSeries {
    id?: number;
    rate?: Rate;
    comment?: string;
    user?: IUser;
    series?: ISeries;
}

export class FollowedSeries implements IFollowedSeries {
    constructor(public id?: number, public rate?: Rate, public comment?: string, public user?: IUser, public series?: ISeries) {}
}

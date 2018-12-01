import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IFollowedSeries } from 'app/shared/model/followed-series.model';

type EntityResponseType = HttpResponse<IFollowedSeries>;
type EntityArrayResponseType = HttpResponse<IFollowedSeries[]>;

@Injectable({ providedIn: 'root' })
export class FollowedSeriesService {
    public resourceUrl = SERVER_API_URL + 'api/followed-series';

    constructor(private http: HttpClient) {}

    create(followedSeries: IFollowedSeries): Observable<EntityResponseType> {
        return this.http.post<IFollowedSeries>(this.resourceUrl, followedSeries, { observe: 'response' });
    }

    update(followedSeries: IFollowedSeries): Observable<EntityResponseType> {
        return this.http.put<IFollowedSeries>(this.resourceUrl, followedSeries, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IFollowedSeries>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IFollowedSeries[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

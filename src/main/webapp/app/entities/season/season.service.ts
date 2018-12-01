import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISeason } from 'app/shared/model/season.model';

type EntityResponseType = HttpResponse<ISeason>;
type EntityArrayResponseType = HttpResponse<ISeason[]>;

@Injectable({ providedIn: 'root' })
export class SeasonService {
    public resourceUrl = SERVER_API_URL + 'api/seasons';

    constructor(private http: HttpClient) {}

    create(season: ISeason): Observable<EntityResponseType> {
        return this.http.post<ISeason>(this.resourceUrl, season, { observe: 'response' });
    }

    update(season: ISeason): Observable<EntityResponseType> {
        return this.http.put<ISeason>(this.resourceUrl, season, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ISeason>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ISeason[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}

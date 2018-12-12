import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes} from '@angular/router';
import {UserRouteAccessService} from 'app/core';
import {Observable, of} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {Series} from 'app/shared/model/series.model';
import {SeriesService} from './series.service';
import {SeriesComponent} from './series.component';
import {SeriesDetailComponent} from './series-detail.component';
import {SeriesUpdateComponent} from './series-update.component';
import {SeriesDeletePopupComponent} from './series-delete-dialog.component';
import {ISeries} from 'app/shared/model/series.model';

@Injectable({providedIn: 'root'})
export class SeriesResolve implements Resolve<ISeries> {
    constructor(private service: SeriesService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Series> {
        const id = route.params['id'] ? route.params['id'] : null;
        const year = route.params['year'] ? route.params['year'] : null;
        const name = route.params['name'] ? route.params['name'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Series>) => response.ok),
                map((series: HttpResponse<Series>) => series.body)
            );
        }
        else if (name && year) {
            return this.service.findByNameAndReleaseYear(name, year).pipe(
                filter((response: HttpResponse<Series>) => response.ok),
                map((series: HttpResponse<Series>) => series.body)
            );
        }
        return of(new Series());
    }
}

export const seriesRoute: Routes = [
    {
        path: 'series',
        component: SeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'series/:id/view',
        component: SeriesDetailComponent,
        resolve: {
            series: SeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'series/:year/:name',
        component: SeriesDetailComponent,
        resolve: {
            series: SeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'series/new',
        component: SeriesUpdateComponent,
        resolve: {
            series: SeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'series/:id/edit',
        component: SeriesUpdateComponent,
        resolve: {
            series: SeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seriesPopupRoute: Routes = [
    {
        path: 'series/:id/delete',
        component: SeriesDeletePopupComponent,
        resolve: {
            series: SeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Series'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { FollowedSeries } from 'app/shared/model/followed-series.model';
import { FollowedSeriesService } from './followed-series.service';
import { FollowedSeriesComponent } from './followed-series.component';
import { FollowedSeriesDetailComponent } from './followed-series-detail.component';
import { FollowedSeriesUpdateComponent } from './followed-series-update.component';
import { FollowedSeriesDeletePopupComponent } from './followed-series-delete-dialog.component';
import { IFollowedSeries } from 'app/shared/model/followed-series.model';

@Injectable({ providedIn: 'root' })
export class FollowedSeriesResolve implements Resolve<IFollowedSeries> {
    constructor(private service: FollowedSeriesService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<FollowedSeries> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<FollowedSeries>) => response.ok),
                map((followedSeries: HttpResponse<FollowedSeries>) => followedSeries.body)
            );
        }
        return of(new FollowedSeries());
    }
}

export const followedSeriesRoute: Routes = [
    {
        path: 'followed-series',
        component: FollowedSeriesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FollowedSeries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'followed-series/:id/view',
        component: FollowedSeriesDetailComponent,
        resolve: {
            followedSeries: FollowedSeriesResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FollowedSeries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'followed-series/new',
        component: FollowedSeriesUpdateComponent,
        resolve: {
            followedSeries: FollowedSeriesResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'FollowedSeries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'followed-series/:id/edit',
        component: FollowedSeriesUpdateComponent,
        resolve: {
            followedSeries: FollowedSeriesResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'FollowedSeries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const followedSeriesPopupRoute: Routes = [
    {
        path: 'followed-series/:id/delete',
        component: FollowedSeriesDeletePopupComponent,
        resolve: {
            followedSeries: FollowedSeriesResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'FollowedSeries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

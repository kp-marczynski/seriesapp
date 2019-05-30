import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Season } from 'app/shared/model/season.model';
import { SeasonService } from './season.service';
import { SeasonComponent } from './season.component';
import { SeasonDetailComponent } from './season-detail.component';
import { SeasonUpdateComponent } from './season-update.component';
import { SeasonDeletePopupComponent } from './season-delete-dialog.component';
import { ISeason } from 'app/shared/model/season.model';

@Injectable({ providedIn: 'root' })
export class SeasonResolve implements Resolve<ISeason> {
    constructor(private service: SeasonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Season> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Season>) => response.ok),
                map((season: HttpResponse<Season>) => season.body)
            );
        }
        return of(new Season());
    }
}

export const seasonRoute: Routes = [
    {
        path: 'season',
        component: SeasonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'season/:id/view',
        component: SeasonDetailComponent,
        resolve: {
            season: SeasonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Seasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'season/new',
        component: SeasonUpdateComponent,
        resolve: {
            season: SeasonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Seasons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'season/:id/edit',
        component: SeasonUpdateComponent,
        resolve: {
            season: SeasonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Seasons'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seasonPopupRoute: Routes = [
    {
        path: 'season/:id/delete',
        component: SeasonDeletePopupComponent,
        resolve: {
            season: SeasonResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'Seasons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

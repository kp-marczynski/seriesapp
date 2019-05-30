import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { WatchedEpisode } from 'app/shared/model/watched-episode.model';
import { WatchedEpisodeService } from './watched-episode.service';
import { WatchedEpisodeComponent } from './watched-episode.component';
import { WatchedEpisodeDetailComponent } from './watched-episode-detail.component';
import { WatchedEpisodeUpdateComponent } from './watched-episode-update.component';
import { WatchedEpisodeDeletePopupComponent } from './watched-episode-delete-dialog.component';
import { IWatchedEpisode } from 'app/shared/model/watched-episode.model';

@Injectable({ providedIn: 'root' })
export class WatchedEpisodeResolve implements Resolve<IWatchedEpisode> {
    constructor(private service: WatchedEpisodeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WatchedEpisode> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<WatchedEpisode>) => response.ok),
                map((watchedEpisode: HttpResponse<WatchedEpisode>) => watchedEpisode.body)
            );
        }
        return of(new WatchedEpisode());
    }
}

export const watchedEpisodeRoute: Routes = [
    {
        path: 'watched-episode',
        component: WatchedEpisodeComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'WatchedEpisodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'watched-episode/:id/view',
        component: WatchedEpisodeDetailComponent,
        resolve: {
            watchedEpisode: WatchedEpisodeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'WatchedEpisodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'watched-episode/new',
        component: WatchedEpisodeUpdateComponent,
        resolve: {
            watchedEpisode: WatchedEpisodeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'WatchedEpisodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'watched-episode/:id/edit',
        component: WatchedEpisodeUpdateComponent,
        resolve: {
            watchedEpisode: WatchedEpisodeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'WatchedEpisodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const watchedEpisodePopupRoute: Routes = [
    {
        path: 'watched-episode/:id/delete',
        component: WatchedEpisodeDeletePopupComponent,
        resolve: {
            watchedEpisode: WatchedEpisodeResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'WatchedEpisodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

import {Injectable} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes} from '@angular/router';
import {UserRouteAccessService} from 'app/core';
import {Observable, of} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {Episode, IEpisode} from 'app/shared/model/episode.model';
import {EpisodeService} from './episode.service';
import {EpisodeComponent} from './episode.component';
import {EpisodeDetailComponent} from './episode-detail.component';
import {EpisodeUpdateComponent} from './episode-update.component';
import {EpisodeDeletePopupComponent} from './episode-delete-dialog.component';

@Injectable({providedIn: 'root'})
export class EpisodeResolve implements Resolve<IEpisode> {
    constructor(private service: EpisodeService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Episode> {
        const id = route.params['id'] ? route.params['id'] : null;
        const year = route.params['year'] ? route.params['year'] : null;
        const name = route.params['name'] ? route.params['name'] : null;
        const seasonNumber = route.params['seasonNumber'] ? route.params['seasonNumber'] : null;
        const episodeNumber = route.params['episodeNumber'] ? route.params['episodeNumber'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Episode>) => response.ok),
                map((episode: HttpResponse<Episode>) => episode.body)
            );
        }
        else if (year && name && seasonNumber && episodeNumber) {
            return this.service.findFromSeries(year, name, seasonNumber, episodeNumber).pipe(
                filter((response: HttpResponse<Episode>) => response.ok),
                map((episode: HttpResponse<Episode>) => episode.body)
            );
        }
        return of(new Episode());
    }
}

export const episodeRoute: Routes = [
    {
        path: 'episode',
        component: EpisodeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'episode/:id/view',
        component: EpisodeDetailComponent,
        resolve: {
            episode: EpisodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'series/:year/:name/season/:seasonNumber/episode/:episodeNumber',
        component: EpisodeDetailComponent,
        resolve: {
            episode: EpisodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'episode/new',
        component: EpisodeUpdateComponent,
        resolve: {
            episode: EpisodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'episode/:id/edit',
        component: EpisodeUpdateComponent,
        resolve: {
            episode: EpisodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const episodePopupRoute: Routes = [
    {
        path: 'episode/:id/delete',
        component: EpisodeDeletePopupComponent,
        resolve: {
            episode: EpisodeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Episodes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];

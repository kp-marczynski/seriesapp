import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IWatchedEpisode } from 'app/shared/model/watched-episode.model';
import { WatchedEpisodeService } from './watched-episode.service';
import { IUser, UserService } from 'app/core';
import { IEpisode } from 'app/shared/model/episode.model';
import { EpisodeService } from 'app/entities/episode';

@Component({
    selector: 'jhi-watched-episode-update',
    templateUrl: './watched-episode-update.component.html'
})
export class WatchedEpisodeUpdateComponent implements OnInit {
    watchedEpisode: IWatchedEpisode;
    isSaving: boolean;

    users: IUser[];

    episodes: IEpisode[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private watchedEpisodeService: WatchedEpisodeService,
        private userService: UserService,
        private episodeService: EpisodeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ watchedEpisode }) => {
            this.watchedEpisode = watchedEpisode;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.episodeService.query().subscribe(
            (res: HttpResponse<IEpisode[]>) => {
                this.episodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.watchedEpisode.id !== undefined) {
            this.subscribeToSaveResponse(this.watchedEpisodeService.update(this.watchedEpisode));
        } else {
            this.subscribeToSaveResponse(this.watchedEpisodeService.create(this.watchedEpisode));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IWatchedEpisode>>) {
        result.subscribe((res: HttpResponse<IWatchedEpisode>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEpisodeById(index: number, item: IEpisode) {
        return item.id;
    }
}

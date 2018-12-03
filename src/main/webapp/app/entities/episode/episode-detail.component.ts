import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IEpisode} from 'app/shared/model/episode.model';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IWatchedEpisode, WatchedEpisode} from "app/shared/model/watched-episode.model";
import {JhiAlertService} from "ng-jhipster";
import {EpisodeService} from "app/entities/episode/episode.service";

@Component({
    selector: 'jhi-episode-detail',
    templateUrl: './episode-detail.component.html'
})
export class EpisodeDetailComponent implements OnInit {
    episode: IEpisode;
    watchedEpisode: IWatchedEpisode;

    constructor(private activatedRoute: ActivatedRoute, private episodeService: EpisodeService, private jhiAlertService: JhiAlertService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({episode}) => {
            this.episode = episode;
            this.loadWatched();
        });
    }

    loadWatched() {
        this.episodeService.findWatched(this.episode.id).subscribe(
            (res: HttpResponse<IWatchedEpisode>) => {
                this.watchedEpisode = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    previousState() {
        window.history.back();
    }
}

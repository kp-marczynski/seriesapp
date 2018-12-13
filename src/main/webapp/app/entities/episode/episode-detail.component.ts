import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {IEpisode} from 'app/shared/model/episode.model';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {IWatchedEpisode, Rate, WatchedEpisode} from "app/shared/model/watched-episode.model";
import {JhiAlertService} from "ng-jhipster";
import {EpisodeService} from "app/entities/episode/episode.service";
import {WatchedEpisodeService} from "app/entities/watched-episode";

@Component({
    selector: 'jhi-episode-detail',
    templateUrl: './episode-detail.component.html'
})
export class EpisodeDetailComponent implements OnInit {
    episode: IEpisode;
    watchedEpisode: IWatchedEpisode;
    averageRate: number;
    private rateCount: number;

    constructor(private activatedRoute: ActivatedRoute, private episodeService: EpisodeService, private jhiAlertService: JhiAlertService, private watchedEpisodeService: WatchedEpisodeService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({episode}) => {
            console.log("Episode: " + episode);
            this.episode = episode;
            this.loadWatched();
            this.updateComunityRate();
        });
    }

    shouldStarBeFilled(starNr: number): boolean {
        if (this.watchedEpisode.rate) {
            switch (this.watchedEpisode.rate) {
                case 'BAD':
                    return starNr <= 1;
                case 'MEDIOCRE':
                    return starNr <= 2;
                case 'AVERAGE':
                    return starNr <= 3;
                case 'GOOD':
                    return starNr <= 4;
                case 'AWESOME':
                    return starNr <= 5;
            }
        }
        else return false;
    }

    setRate(i: number) {
        switch (i) {
            case 1:
                this.watchedEpisode.rate = Rate.BAD;
                break;
            case 2:
                this.watchedEpisode.rate = Rate.MEDIOCRE;
                break;
            case 3:
                this.watchedEpisode.rate = Rate.AVERAGE;
                break;
            case 4:
                this.watchedEpisode.rate = Rate.GOOD;
                break;
            case 5:
                this.watchedEpisode.rate = Rate.AWESOME;
                break;
        }
        this.savewatchedEpisode();
    }

    savewatchedEpisode() {
        if (this.watchedEpisode.id) {
            this.watchedEpisodeService.update(this.watchedEpisode).subscribe(
                (res: HttpResponse<IWatchedEpisode>) => {
                    this.watchedEpisode = res.body;
                    this.updateComunityRate();
                    console.log("updated");
                }
            );
        } else {
            this.watchedEpisodeService.create(this.watchedEpisode).subscribe((res: HttpResponse<IWatchedEpisode>) => {
                this.watchedEpisode = res.body;
                this.updateComunityRate();
                console.log("saved");
            });
        }
    }

    saveComment(value: string) {
        this.watchedEpisode.comment = value;
        this.savewatchedEpisode();
    }

    setFollowed() {
        this.savewatchedEpisode();
    }

    updateComunityRate() {
        this.getAverageRate();
        this.getRateCount();
    }

    getAverageRate() {
        if (this.episode && this.episode.id) {
            this.watchedEpisodeService.getAverageRate(this.episode.id).subscribe(
                (res: HttpResponse<any>) => {
                    this.averageRate = res.body;
                }
            )
        }
    }

    getRateCount() {
        if (this.episode && this.episode.id) {
            this.watchedEpisodeService.getRateCount(this.episode.id).subscribe(
                (res: HttpResponse<any>) => {
                    this.rateCount = res.body;
                }
            )
        }
    }

    loadWatched() {
        this.watchedEpisodeService.findByEpisodeId(this.episode.id).subscribe(
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

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISeries} from 'app/shared/model/series.model';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SeriesService} from "app/entities/series/series.service";
import {IFollowedSeries} from "app/shared/model/followed-series.model";
import {JhiAlertService} from "ng-jhipster";

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html'
})
export class SeriesDetailComponent implements OnInit {
    series: ISeries;
    visibleSeason: number = null;
    followedSeries: IFollowedSeries;

    constructor(private activatedRoute: ActivatedRoute, private seriesService: SeriesService, private jhiAlertService: JhiAlertService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({series}) => {
            this.series = series;
            this.loadFollowed();
            console.log(series);
        });
    }

    loadFollowed() {
        this.seriesService.findFollowed(this.series.id).subscribe(
            (res: HttpResponse<IFollowedSeries>) => {
                this.followedSeries = res.body;
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

    setVisible(seasonNumber: number) {
        this.visibleSeason = (this.visibleSeason == seasonNumber) ? null : seasonNumber;
    }
}

import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISeries} from 'app/shared/model/series.model';
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";
import {SeriesService} from "app/entities/series/series.service";
import {IFollowedSeries} from "app/shared/model/followed-series.model";
import {JhiAlertService} from "ng-jhipster";
import {FollowedSeriesService} from "app/entities/followed-series";

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html'
})
export class SeriesDetailComponent implements OnInit {
    series: ISeries;
    visibleSeason: number = null;
    followedSeries: IFollowedSeries;
    averageRate: number = null;

    constructor(private activatedRoute: ActivatedRoute, private seriesService: SeriesService, private jhiAlertService: JhiAlertService, private followedSeriesService: FollowedSeriesService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({series}) => {
            this.series = series;
            this.loadFollowed();
            this.getAverageRate()
            console.log(series);
        });
    }

    shouldStarBeFilled(starNr: number): boolean {
        if (this.followedSeries.rate) {
            switch (this.followedSeries.rate) {
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

    getAverageRate() {
        if (this.series && this.series.id) {
            this.followedSeriesService.getAverageRate(this.series.id).subscribe(
                (res: HttpResponse<any>) => {
                    this.averageRate = res.body;
                }
            )
        }
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

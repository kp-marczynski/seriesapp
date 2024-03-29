import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISeries} from 'app/shared/model/series.model';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {SeriesService} from 'app/entities/series/series.service';
import {IFollowedSeries, Rate} from 'app/shared/model/followed-series.model';
import {JhiAlertService} from 'ng-jhipster';
import {FollowedSeriesService} from 'app/entities/followed-series';
import {ErrorModalService} from 'app/core/error-modal/error-modal.service';

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html'
})
export class SeriesDetailComponent implements OnInit {
    series: ISeries;
    visibleSeason: number = null;
    followedSeries: IFollowedSeries;
    averageRate: number = null;
    private rateCount: number;

    constructor(private activatedRoute: ActivatedRoute,
                private seriesService: SeriesService,
                private jhiAlertService: JhiAlertService,
                private followedSeriesService: FollowedSeriesService,
                private errorModalService: ErrorModalService) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({series}) => {
            this.series = series;
            this.loadFollowed();
            this.updateComunityRate();
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

    setRate(i: number) {
        if (this.followedSeries.id && this.followedSeries.rate) {
            this.errorModalService.open("Once rated, series can't be rated again!");
        }
        else {
            switch (i) {
                case 1:
                    this.followedSeries.rate = Rate.BAD;
                    break;
                case 2:
                    this.followedSeries.rate = Rate.MEDIOCRE;
                    break;
                case 3:
                    this.followedSeries.rate = Rate.AVERAGE;
                    break;
                case 4:
                    this.followedSeries.rate = Rate.GOOD;
                    break;
                case 5:
                    this.followedSeries.rate = Rate.AWESOME;
                    break;
            }
            this.saveFollowedSeries();
        }
    }

    saveFollowedSeries() {
        if (this.followedSeries.id) {
            this.followedSeriesService.update(this.followedSeries).subscribe(
                (res: HttpResponse<IFollowedSeries>) => {
                    this.followedSeries = res.body;
                    this.updateComunityRate();
                }
            );
        } else {
            this.followedSeriesService.create(this.followedSeries).subscribe((res: HttpResponse<IFollowedSeries>) => {
                this.followedSeries = res.body;
                this.updateComunityRate();
            });
        }
    }

    saveComment(value: string) {
        if (this.followedSeries.id && this.followedSeries.comment) {
            this.errorModalService.open("Once commented, series can't be commented again!");
        }
        else {
            this.followedSeries.comment = value;
            this.saveFollowedSeries();
        }
    }

    setFollowed() {
        if (this.followedSeries && this.followedSeries.id) {
            this.errorModalService.open("Once followed, series can't be followed again!");
        }
        else {
            this.saveFollowedSeries();
        }
    }

    updateComunityRate() {
        this.getAverageRate();
        this.getRateCount();
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

    getRateCount() {
        if (this.series && this.series.id) {
            this.followedSeriesService.getRateCount(this.series.id).subscribe(
                (res: HttpResponse<any>) => {
                    this.rateCount = res.body;
                }
            )
        }
    }

    loadFollowed() {
        this.followedSeriesService.findBySeriesId(this.series.id).subscribe(
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

    showErrorModal() {
        if (this.followedSeries.id && this.followedSeries.comment) {
            this.errorModalService.open("Once commented, series can't be commented again!");
        }
    }
}

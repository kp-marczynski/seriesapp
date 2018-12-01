import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IFollowedSeries } from 'app/shared/model/followed-series.model';
import { FollowedSeriesService } from './followed-series.service';
import { IUser, UserService } from 'app/core';
import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from 'app/entities/series';

@Component({
    selector: 'jhi-followed-series-update',
    templateUrl: './followed-series-update.component.html'
})
export class FollowedSeriesUpdateComponent implements OnInit {
    followedSeries: IFollowedSeries;
    isSaving: boolean;

    users: IUser[];

    series: ISeries[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private followedSeriesService: FollowedSeriesService,
        private userService: UserService,
        private seriesService: SeriesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ followedSeries }) => {
            this.followedSeries = followedSeries;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.seriesService.query().subscribe(
            (res: HttpResponse<ISeries[]>) => {
                this.series = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.followedSeries.id !== undefined) {
            this.subscribeToSaveResponse(this.followedSeriesService.update(this.followedSeries));
        } else {
            this.subscribeToSaveResponse(this.followedSeriesService.create(this.followedSeries));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFollowedSeries>>) {
        result.subscribe((res: HttpResponse<IFollowedSeries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSeriesById(index: number, item: ISeries) {
        return item.id;
    }
}

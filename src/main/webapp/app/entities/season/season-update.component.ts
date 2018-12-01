import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISeason } from 'app/shared/model/season.model';
import { SeasonService } from './season.service';
import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from 'app/entities/series';

@Component({
    selector: 'jhi-season-update',
    templateUrl: './season-update.component.html'
})
export class SeasonUpdateComponent implements OnInit {
    season: ISeason;
    isSaving: boolean;

    series: ISeries[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private seasonService: SeasonService,
        private seriesService: SeriesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ season }) => {
            this.season = season;
        });
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
        if (this.season.id !== undefined) {
            this.subscribeToSaveResponse(this.seasonService.update(this.season));
        } else {
            this.subscribeToSaveResponse(this.seasonService.create(this.season));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeason>>) {
        result.subscribe((res: HttpResponse<ISeason>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSeriesById(index: number, item: ISeries) {
        return item.id;
    }
}

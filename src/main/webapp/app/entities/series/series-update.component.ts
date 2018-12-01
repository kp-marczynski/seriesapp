import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from './series.service';

@Component({
    selector: 'jhi-series-update',
    templateUrl: './series-update.component.html'
})
export class SeriesUpdateComponent implements OnInit {
    series: ISeries;
    isSaving: boolean;

    constructor(private seriesService: SeriesService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ series }) => {
            this.series = series;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.series.id !== undefined) {
            this.subscribeToSaveResponse(this.seriesService.update(this.series));
        } else {
            this.subscribeToSaveResponse(this.seriesService.create(this.series));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISeries>>) {
        result.subscribe((res: HttpResponse<ISeries>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

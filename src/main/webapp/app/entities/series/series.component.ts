import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeries } from 'app/shared/model/series.model';
import { Principal } from 'app/core';
import { SeriesService } from './series.service';

@Component({
    selector: 'jhi-series',
    templateUrl: './series.component.html'
})
export class SeriesComponent implements OnInit, OnDestroy {
    series: ISeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seriesService: SeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.seriesService.query().subscribe(
            (res: HttpResponse<ISeries[]>) => {
                this.series = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeries) {
        return item.id;
    }

    registerChangeInSeries() {
        this.eventSubscriber = this.eventManager.subscribe('seriesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

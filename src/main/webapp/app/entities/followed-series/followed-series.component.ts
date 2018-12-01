import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFollowedSeries } from 'app/shared/model/followed-series.model';
import { Principal } from 'app/core';
import { FollowedSeriesService } from './followed-series.service';

@Component({
    selector: 'jhi-followed-series',
    templateUrl: './followed-series.component.html'
})
export class FollowedSeriesComponent implements OnInit, OnDestroy {
    followedSeries: IFollowedSeries[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private followedSeriesService: FollowedSeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.followedSeriesService.query().subscribe(
            (res: HttpResponse<IFollowedSeries[]>) => {
                this.followedSeries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFollowedSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFollowedSeries) {
        return item.id;
    }

    registerChangeInFollowedSeries() {
        this.eventSubscriber = this.eventManager.subscribe('followedSeriesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

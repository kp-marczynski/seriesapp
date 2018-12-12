import {Component, OnDestroy, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {IFollowedSeries} from 'app/shared/model/followed-series.model';
import {Principal} from 'app/core';
import {FollowedSeriesService} from './followed-series.service';

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
    ) {
    }

    loadAll() {
        this.followedSeriesService.query().subscribe(
            (res: HttpResponse<IFollowedSeries[]>) => {
                this.followedSeries = res.body;
                this.followedSeries.sort((a, b) => {
                    if (a.series.name > b.series.name) {
                        return 1;
                    }
                    else if (a.series.name < b.series.name) {
                        return -1;
                    }
                    else {
                        if (a.series.releaseYear > b.series.releaseYear) {
                            return 1;
                        }
                        else if (a.series.releaseYear < b.series.releaseYear) {
                            return -1;
                        }
                        else return 0;
                    }
                });
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    getSeriesArray() {
        return this.followedSeries.map((elem) => elem.series);
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

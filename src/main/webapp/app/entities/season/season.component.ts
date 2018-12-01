import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISeason } from 'app/shared/model/season.model';
import { Principal } from 'app/core';
import { SeasonService } from './season.service';

@Component({
    selector: 'jhi-season',
    templateUrl: './season.component.html'
})
export class SeasonComponent implements OnInit, OnDestroy {
    seasons: ISeason[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private seasonService: SeasonService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.seasonService.query().subscribe(
            (res: HttpResponse<ISeason[]>) => {
                this.seasons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeasons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeason) {
        return item.id;
    }

    registerChangeInSeasons() {
        this.eventSubscriber = this.eventManager.subscribe('seasonListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IWatchedEpisode } from 'app/shared/model/watched-episode.model';
import { Principal } from 'app/core';
import { WatchedEpisodeService } from './watched-episode.service';

@Component({
    selector: 'jhi-watched-episode',
    templateUrl: './watched-episode.component.html'
})
export class WatchedEpisodeComponent implements OnInit, OnDestroy {
    watchedEpisodes: IWatchedEpisode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private watchedEpisodeService: WatchedEpisodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.watchedEpisodeService.query().subscribe(
            (res: HttpResponse<IWatchedEpisode[]>) => {
                this.watchedEpisodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInWatchedEpisodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IWatchedEpisode) {
        return item.id;
    }

    registerChangeInWatchedEpisodes() {
        this.eventSubscriber = this.eventManager.subscribe('watchedEpisodeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

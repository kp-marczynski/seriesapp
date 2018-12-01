import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IEpisode } from 'app/shared/model/episode.model';
import { Principal } from 'app/core';
import { EpisodeService } from './episode.service';

@Component({
    selector: 'jhi-episode',
    templateUrl: './episode.component.html'
})
export class EpisodeComponent implements OnInit, OnDestroy {
    episodes: IEpisode[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private episodeService: EpisodeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.episodeService.query().subscribe(
            (res: HttpResponse<IEpisode[]>) => {
                this.episodes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInEpisodes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IEpisode) {
        return item.id;
    }

    registerChangeInEpisodes() {
        this.eventSubscriber = this.eventManager.subscribe('episodeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

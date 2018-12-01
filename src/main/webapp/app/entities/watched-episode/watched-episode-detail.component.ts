import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWatchedEpisode } from 'app/shared/model/watched-episode.model';

@Component({
    selector: 'jhi-watched-episode-detail',
    templateUrl: './watched-episode-detail.component.html'
})
export class WatchedEpisodeDetailComponent implements OnInit {
    watchedEpisode: IWatchedEpisode;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ watchedEpisode }) => {
            this.watchedEpisode = watchedEpisode;
        });
    }

    previousState() {
        window.history.back();
    }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFollowedSeries } from 'app/shared/model/followed-series.model';

@Component({
    selector: 'jhi-followed-series-detail',
    templateUrl: './followed-series-detail.component.html'
})
export class FollowedSeriesDetailComponent implements OnInit {
    followedSeries: IFollowedSeries;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ followedSeries }) => {
            this.followedSeries = followedSeries;
        });
    }

    previousState() {
        window.history.back();
    }
}

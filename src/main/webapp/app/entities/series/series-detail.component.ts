import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISeries} from 'app/shared/model/series.model';

@Component({
    selector: 'jhi-series-detail',
    templateUrl: './series-detail.component.html'
})
export class SeriesDetailComponent implements OnInit {
    series: ISeries;
    visibleSeason: number = null;

    constructor(private activatedRoute: ActivatedRoute) {
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({series}) => {
            this.series = series;
            console.log(series);
        });
    }

    previousState() {
        window.history.back();
    }

    setVisible(seasonNumber: number) {
        this.visibleSeason = (this.visibleSeason == seasonNumber) ? null : seasonNumber;
    }
}

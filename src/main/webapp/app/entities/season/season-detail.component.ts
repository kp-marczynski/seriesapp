import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISeason } from 'app/shared/model/season.model';

@Component({
    selector: 'jhi-season-detail',
    templateUrl: './season-detail.component.html'
})
export class SeasonDetailComponent implements OnInit {
    season: ISeason;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ season }) => {
            this.season = season;
        });
    }

    previousState() {
        window.history.back();
    }
}

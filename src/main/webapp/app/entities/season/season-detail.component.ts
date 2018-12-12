import {Component, OnInit, Input} from '@angular/core';
import {ActivatedRoute} from '@angular/router';

import {ISeason, Season} from 'app/shared/model/season.model';

@Component({
    selector: 'jhi-season-detail',
    templateUrl: './season-detail.component.html'
})
export class SeasonDetailComponent implements OnInit {
    @Input() season: ISeason;
    private  standaloneView: boolean = false;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        if (this.season == null) {
            this.activatedRoute.data.subscribe(({season}) => {
                this.season = season;
            });
            this.standaloneView = true;
        } else{
            this.standaloneView = false;
        }
    }

    previousState() {
        window.history.back();
    }
}

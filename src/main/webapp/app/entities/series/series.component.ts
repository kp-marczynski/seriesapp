import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Subscription} from 'rxjs';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {ISeries} from 'app/shared/model/series.model';
import {Principal} from 'app/core';
import {SeriesService} from './series.service';
import {FollowedSeriesService} from "app/entities/followed-series";
import {IFollowedSeries} from "app/shared/model/followed-series.model";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'jhi-series',
    templateUrl: './series.component.html'
})
export class SeriesComponent implements OnInit, OnDestroy {
    @Input() series: ISeries[];
    currentAccount: any;
    eventSubscriber: Subscription;
    average: Map<number, number> = new Map();
    followed: Set<number> = new Set();

    constructor(
        private seriesService: SeriesService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private followedSeriesService: FollowedSeriesService, private activatedRoute: ActivatedRoute) {
    }

    loadAll() {
        if (!this.series) {
            let search = this.activatedRoute.snapshot.params['search'];
            if(search){
                this.seriesService.search(search).subscribe(
                    (res: HttpResponse<ISeries[]>) => {
                        this.series = res.body;
                        this.getAverageForAllSeries();
                        this.getAllFollowed();
                    }
                )
            }
            else {
                this.seriesService.query().subscribe(
                    (res: HttpResponse<ISeries[]>) => {
                        this.series = res.body;
                        this.series.sort((a, b) => {
                            if (a.name > b.name) {
                                return 1;
                            }
                            else if (a.name < b.name) {
                                return -1;
                            }
                            else {
                                if (a.releaseYear > b.releaseYear) {
                                    return 1;
                                }
                                else if (a.releaseYear < b.releaseYear) {
                                    return -1;
                                }
                                else return 0;
                            }
                        });
                        this.getAverageForAllSeries();
                        this.getAllFollowed();
                    },
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            }
        }
        else {
            this.getAverageForAllSeries();
            this.series.forEach(elem => this.followed.add(elem.id));
        }
    }

    getAverageForAllSeries() {
        this.series.forEach(elem => this.followedSeriesService.getAverageRate(elem.id).subscribe(
            (res: HttpResponse<any>) => {
                this.average.set(elem.id, res.body);
            }
        ))
    }

    getAllFollowed() {
        this.followedSeriesService.query().subscribe(
            (res: HttpResponse<IFollowedSeries[]>) => {
                res.body.forEach(elem => {
                    this.followed.add(elem.series.id);
                })
            }
        )
    }

    setFollowed(seriesId: number) {
        if (!this.followed.has(seriesId)) {
            this.followedSeriesService.findBySeriesId(seriesId).subscribe(
                (res: HttpResponse<IFollowedSeries>) => {
                    this.followedSeriesService.create(res.body).subscribe(
                        (res2: HttpResponse<IFollowedSeries>) => {
                            this.followed.add(res2.body.series.id);
                        }
                    )
                })
        }
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSeries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISeries) {
        return item.id;
    }

    registerChangeInSeries() {
        this.eventSubscriber = this.eventManager.subscribe('seriesListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}

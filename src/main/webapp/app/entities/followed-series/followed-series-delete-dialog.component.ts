import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFollowedSeries } from 'app/shared/model/followed-series.model';
import { FollowedSeriesService } from './followed-series.service';

@Component({
    selector: 'jhi-followed-series-delete-dialog',
    templateUrl: './followed-series-delete-dialog.component.html'
})
export class FollowedSeriesDeleteDialogComponent {
    followedSeries: IFollowedSeries;

    constructor(
        private followedSeriesService: FollowedSeriesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.followedSeriesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'followedSeriesListModification',
                content: 'Deleted an followedSeries'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-followed-series-delete-popup',
    template: ''
})
export class FollowedSeriesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ followedSeries }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FollowedSeriesDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.followedSeries = followedSeries;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}

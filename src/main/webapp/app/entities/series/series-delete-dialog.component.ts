import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISeries } from 'app/shared/model/series.model';
import { SeriesService } from './series.service';

@Component({
    selector: 'jhi-series-delete-dialog',
    templateUrl: './series-delete-dialog.component.html'
})
export class SeriesDeleteDialogComponent {
    series: ISeries;

    constructor(private seriesService: SeriesService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seriesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'seriesListModification',
                content: 'Deleted an series'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-series-delete-popup',
    template: ''
})
export class SeriesDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ series }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SeriesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.series = series;
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

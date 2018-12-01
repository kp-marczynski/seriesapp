import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWatchedEpisode } from 'app/shared/model/watched-episode.model';
import { WatchedEpisodeService } from './watched-episode.service';

@Component({
    selector: 'jhi-watched-episode-delete-dialog',
    templateUrl: './watched-episode-delete-dialog.component.html'
})
export class WatchedEpisodeDeleteDialogComponent {
    watchedEpisode: IWatchedEpisode;

    constructor(
        private watchedEpisodeService: WatchedEpisodeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.watchedEpisodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'watchedEpisodeListModification',
                content: 'Deleted an watchedEpisode'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-watched-episode-delete-popup',
    template: ''
})
export class WatchedEpisodeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ watchedEpisode }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WatchedEpisodeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.watchedEpisode = watchedEpisode;
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

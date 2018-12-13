import {Injectable} from '@angular/core';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';

import {ErrorModalComponent} from 'app/shared/error/error.component';

@Injectable({providedIn: 'root'})
export class ErrorModalService {
    private isOpen = false;

    constructor(private modalService: NgbModal) {
    }

    open(errorText: string): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;
        const modalRef = this.modalService.open(ErrorModalComponent);
        modalRef.componentInstance.myModalProperty = errorText;
        modalRef.result.then(
            result => {
                this.isOpen = false;
            },
            reason => {
                this.isOpen = false;
            }
        );
        return modalRef;
    }
}

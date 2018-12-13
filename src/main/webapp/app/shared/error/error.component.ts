import {Component, Input} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-error-modal',
    templateUrl: './error.component.html'
})
export class ErrorModalComponent {
    @Input() myModalProperty: string;

    constructor(
        public activeModal: NgbActiveModal
    ) {
    }

    cancel() {
        this.activeModal.dismiss('cancel');
    }
}

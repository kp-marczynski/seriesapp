import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {NgbDateAdapter} from '@ng-bootstrap/ng-bootstrap';

import {NgbDateMomentAdapter} from './util/datepicker-adapter';
import {
    HasAnyAuthorityDirective,
    JhiLoginModalComponent,
    SeriesappSharedCommonModule,
    SeriesappSharedLibsModule
} from './';
import {ErrorModalComponent} from "app/shared/error/error.component";

@NgModule({
    imports: [SeriesappSharedLibsModule, SeriesappSharedCommonModule],
    declarations: [JhiLoginModalComponent, ErrorModalComponent, HasAnyAuthorityDirective],
    providers: [{provide: NgbDateAdapter, useClass: NgbDateMomentAdapter}],
    entryComponents: [JhiLoginModalComponent, ErrorModalComponent],
    exports: [SeriesappSharedCommonModule, JhiLoginModalComponent, ErrorModalComponent, HasAnyAuthorityDirective],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappSharedModule {
    static forRoot() {
        return {
            ngModule: SeriesappSharedModule
        };
    }
}

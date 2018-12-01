import { NgModule } from '@angular/core';

import { SeriesappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SeriesappSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SeriesappSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SeriesappSharedCommonModule {}

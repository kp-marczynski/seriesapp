import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeriesappSharedModule } from 'app/shared';
import {
    SeasonComponent,
    SeasonDetailComponent,
    SeasonUpdateComponent,
    SeasonDeletePopupComponent,
    SeasonDeleteDialogComponent,
    seasonRoute,
    seasonPopupRoute
} from './';

const ENTITY_STATES = [...seasonRoute, ...seasonPopupRoute];

@NgModule({
    imports: [SeriesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SeasonComponent, SeasonDetailComponent, SeasonUpdateComponent, SeasonDeleteDialogComponent, SeasonDeletePopupComponent],
    entryComponents: [SeasonComponent, SeasonUpdateComponent, SeasonDeleteDialogComponent, SeasonDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappSeasonModule {}

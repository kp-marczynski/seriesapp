import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeriesappSharedModule } from 'app/shared';
import {
    SeriesComponent,
    SeriesDetailComponent,
    SeriesUpdateComponent,
    SeriesDeletePopupComponent,
    SeriesDeleteDialogComponent,
    seriesRoute,
    seriesPopupRoute
} from './';
import {SeasonDetailComponent} from "app/entities/season";
import {SeriesappSeasonModule} from "app/entities/season/season.module";

const ENTITY_STATES = [...seriesRoute, ...seriesPopupRoute];

@NgModule({
    imports: [SeriesappSharedModule, SeriesappSeasonModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SeriesComponent, SeriesDetailComponent, SeriesUpdateComponent, SeriesDeleteDialogComponent, SeriesDeletePopupComponent],
    entryComponents: [SeriesComponent, SeriesUpdateComponent, SeriesDeleteDialogComponent, SeriesDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappSeriesModule {}

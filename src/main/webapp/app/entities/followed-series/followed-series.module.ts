import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeriesappSharedModule } from 'app/shared';
import { SeriesappAdminModule } from 'app/admin/admin.module';
import {
    FollowedSeriesComponent,
    FollowedSeriesDetailComponent,
    FollowedSeriesUpdateComponent,
    FollowedSeriesDeletePopupComponent,
    FollowedSeriesDeleteDialogComponent,
    followedSeriesRoute,
    followedSeriesPopupRoute
} from './';

const ENTITY_STATES = [...followedSeriesRoute, ...followedSeriesPopupRoute];

@NgModule({
    imports: [SeriesappSharedModule, SeriesappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        FollowedSeriesComponent,
        FollowedSeriesDetailComponent,
        FollowedSeriesUpdateComponent,
        FollowedSeriesDeleteDialogComponent,
        FollowedSeriesDeletePopupComponent
    ],
    entryComponents: [
        FollowedSeriesComponent,
        FollowedSeriesUpdateComponent,
        FollowedSeriesDeleteDialogComponent,
        FollowedSeriesDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappFollowedSeriesModule {}

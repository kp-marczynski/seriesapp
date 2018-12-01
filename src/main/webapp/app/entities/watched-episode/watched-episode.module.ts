import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeriesappSharedModule } from 'app/shared';
import { SeriesappAdminModule } from 'app/admin/admin.module';
import {
    WatchedEpisodeComponent,
    WatchedEpisodeDetailComponent,
    WatchedEpisodeUpdateComponent,
    WatchedEpisodeDeletePopupComponent,
    WatchedEpisodeDeleteDialogComponent,
    watchedEpisodeRoute,
    watchedEpisodePopupRoute
} from './';

const ENTITY_STATES = [...watchedEpisodeRoute, ...watchedEpisodePopupRoute];

@NgModule({
    imports: [SeriesappSharedModule, SeriesappAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WatchedEpisodeComponent,
        WatchedEpisodeDetailComponent,
        WatchedEpisodeUpdateComponent,
        WatchedEpisodeDeleteDialogComponent,
        WatchedEpisodeDeletePopupComponent
    ],
    entryComponents: [
        WatchedEpisodeComponent,
        WatchedEpisodeUpdateComponent,
        WatchedEpisodeDeleteDialogComponent,
        WatchedEpisodeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappWatchedEpisodeModule {}

import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SeriesappSharedModule } from 'app/shared';
import {
    EpisodeComponent,
    EpisodeDetailComponent,
    EpisodeUpdateComponent,
    EpisodeDeletePopupComponent,
    EpisodeDeleteDialogComponent,
    episodeRoute,
    episodePopupRoute
} from './';

const ENTITY_STATES = [...episodeRoute, ...episodePopupRoute];

@NgModule({
    imports: [SeriesappSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        EpisodeComponent,
        EpisodeDetailComponent,
        EpisodeUpdateComponent,
        EpisodeDeleteDialogComponent,
        EpisodeDeletePopupComponent
    ],
    entryComponents: [EpisodeComponent, EpisodeUpdateComponent, EpisodeDeleteDialogComponent, EpisodeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappEpisodeModule {}

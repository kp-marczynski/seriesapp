import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SeriesappSeriesModule } from './series/series.module';
import { SeriesappSeasonModule } from './season/season.module';
import { SeriesappEpisodeModule } from './episode/episode.module';
import { SeriesappFollowedSeriesModule } from './followed-series/followed-series.module';
import { SeriesappWatchedEpisodeModule } from './watched-episode/watched-episode.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SeriesappSeriesModule,
        SeriesappSeasonModule,
        SeriesappEpisodeModule,
        SeriesappFollowedSeriesModule,
        SeriesappWatchedEpisodeModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SeriesappEntityModule {}

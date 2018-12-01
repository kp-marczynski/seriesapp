/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeriesappTestModule } from '../../../test.module';
import { WatchedEpisodeComponent } from 'app/entities/watched-episode/watched-episode.component';
import { WatchedEpisodeService } from 'app/entities/watched-episode/watched-episode.service';
import { WatchedEpisode } from 'app/shared/model/watched-episode.model';

describe('Component Tests', () => {
    describe('WatchedEpisode Management Component', () => {
        let comp: WatchedEpisodeComponent;
        let fixture: ComponentFixture<WatchedEpisodeComponent>;
        let service: WatchedEpisodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [WatchedEpisodeComponent],
                providers: []
            })
                .overrideTemplate(WatchedEpisodeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WatchedEpisodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WatchedEpisodeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new WatchedEpisode(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.watchedEpisodes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

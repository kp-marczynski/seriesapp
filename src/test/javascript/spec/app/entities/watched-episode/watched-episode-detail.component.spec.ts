/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { WatchedEpisodeDetailComponent } from 'app/entities/watched-episode/watched-episode-detail.component';
import { WatchedEpisode } from 'app/shared/model/watched-episode.model';

describe('Component Tests', () => {
    describe('WatchedEpisode Management Detail Component', () => {
        let comp: WatchedEpisodeDetailComponent;
        let fixture: ComponentFixture<WatchedEpisodeDetailComponent>;
        const route = ({ data: of({ watchedEpisode: new WatchedEpisode(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [WatchedEpisodeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(WatchedEpisodeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WatchedEpisodeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.watchedEpisode).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

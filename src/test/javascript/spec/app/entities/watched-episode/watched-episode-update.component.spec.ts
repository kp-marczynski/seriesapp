/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { WatchedEpisodeUpdateComponent } from 'app/entities/watched-episode/watched-episode-update.component';
import { WatchedEpisodeService } from 'app/entities/watched-episode/watched-episode.service';
import { WatchedEpisode } from 'app/shared/model/watched-episode.model';

describe('Component Tests', () => {
    describe('WatchedEpisode Management Update Component', () => {
        let comp: WatchedEpisodeUpdateComponent;
        let fixture: ComponentFixture<WatchedEpisodeUpdateComponent>;
        let service: WatchedEpisodeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [WatchedEpisodeUpdateComponent]
            })
                .overrideTemplate(WatchedEpisodeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WatchedEpisodeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WatchedEpisodeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WatchedEpisode(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.watchedEpisode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WatchedEpisode();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.watchedEpisode = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

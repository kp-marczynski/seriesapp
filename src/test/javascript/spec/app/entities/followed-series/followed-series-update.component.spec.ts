/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { FollowedSeriesUpdateComponent } from 'app/entities/followed-series/followed-series-update.component';
import { FollowedSeriesService } from 'app/entities/followed-series/followed-series.service';
import { FollowedSeries } from 'app/shared/model/followed-series.model';

describe('Component Tests', () => {
    describe('FollowedSeries Management Update Component', () => {
        let comp: FollowedSeriesUpdateComponent;
        let fixture: ComponentFixture<FollowedSeriesUpdateComponent>;
        let service: FollowedSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [FollowedSeriesUpdateComponent]
            })
                .overrideTemplate(FollowedSeriesUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FollowedSeriesUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowedSeriesService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FollowedSeries(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.followedSeries = entity;
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
                    const entity = new FollowedSeries();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.followedSeries = entity;
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

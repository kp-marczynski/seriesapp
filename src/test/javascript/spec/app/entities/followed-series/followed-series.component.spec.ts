/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeriesappTestModule } from '../../../test.module';
import { FollowedSeriesComponent } from 'app/entities/followed-series/followed-series.component';
import { FollowedSeriesService } from 'app/entities/followed-series/followed-series.service';
import { FollowedSeries } from 'app/shared/model/followed-series.model';

describe('Component Tests', () => {
    describe('FollowedSeries Management Component', () => {
        let comp: FollowedSeriesComponent;
        let fixture: ComponentFixture<FollowedSeriesComponent>;
        let service: FollowedSeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [FollowedSeriesComponent],
                providers: []
            })
                .overrideTemplate(FollowedSeriesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FollowedSeriesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowedSeriesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FollowedSeries(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.followedSeries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

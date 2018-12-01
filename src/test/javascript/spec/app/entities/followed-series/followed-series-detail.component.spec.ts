/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { FollowedSeriesDetailComponent } from 'app/entities/followed-series/followed-series-detail.component';
import { FollowedSeries } from 'app/shared/model/followed-series.model';

describe('Component Tests', () => {
    describe('FollowedSeries Management Detail Component', () => {
        let comp: FollowedSeriesDetailComponent;
        let fixture: ComponentFixture<FollowedSeriesDetailComponent>;
        const route = ({ data: of({ followedSeries: new FollowedSeries(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [FollowedSeriesDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FollowedSeriesDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FollowedSeriesDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.followedSeries).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

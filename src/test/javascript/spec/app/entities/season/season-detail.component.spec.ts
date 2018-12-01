/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { SeasonDetailComponent } from 'app/entities/season/season-detail.component';
import { Season } from 'app/shared/model/season.model';

describe('Component Tests', () => {
    describe('Season Management Detail Component', () => {
        let comp: SeasonDetailComponent;
        let fixture: ComponentFixture<SeasonDetailComponent>;
        const route = ({ data: of({ season: new Season(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [SeasonDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SeasonDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SeasonDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.season).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});

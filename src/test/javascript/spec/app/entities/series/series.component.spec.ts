/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SeriesappTestModule } from '../../../test.module';
import { SeriesComponent } from 'app/entities/series/series.component';
import { SeriesService } from 'app/entities/series/series.service';
import { Series } from 'app/shared/model/series.model';

describe('Component Tests', () => {
    describe('Series Management Component', () => {
        let comp: SeriesComponent;
        let fixture: ComponentFixture<SeriesComponent>;
        let service: SeriesService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [SeriesComponent],
                providers: []
            })
                .overrideTemplate(SeriesComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeriesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeriesService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Series(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.series[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});

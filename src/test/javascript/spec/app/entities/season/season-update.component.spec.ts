/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SeriesappTestModule } from '../../../test.module';
import { SeasonUpdateComponent } from 'app/entities/season/season-update.component';
import { SeasonService } from 'app/entities/season/season.service';
import { Season } from 'app/shared/model/season.model';

describe('Component Tests', () => {
    describe('Season Management Update Component', () => {
        let comp: SeasonUpdateComponent;
        let fixture: ComponentFixture<SeasonUpdateComponent>;
        let service: SeasonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [SeasonUpdateComponent]
            })
                .overrideTemplate(SeasonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SeasonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeasonService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Season(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.season = entity;
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
                    const entity = new Season();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.season = entity;
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

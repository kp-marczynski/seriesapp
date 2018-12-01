/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesappTestModule } from '../../../test.module';
import { FollowedSeriesDeleteDialogComponent } from 'app/entities/followed-series/followed-series-delete-dialog.component';
import { FollowedSeriesService } from 'app/entities/followed-series/followed-series.service';

describe('Component Tests', () => {
    describe('FollowedSeries Management Delete Component', () => {
        let comp: FollowedSeriesDeleteDialogComponent;
        let fixture: ComponentFixture<FollowedSeriesDeleteDialogComponent>;
        let service: FollowedSeriesService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [FollowedSeriesDeleteDialogComponent]
            })
                .overrideTemplate(FollowedSeriesDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FollowedSeriesDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FollowedSeriesService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});

/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SeriesappTestModule } from '../../../test.module';
import { WatchedEpisodeDeleteDialogComponent } from 'app/entities/watched-episode/watched-episode-delete-dialog.component';
import { WatchedEpisodeService } from 'app/entities/watched-episode/watched-episode.service';

describe('Component Tests', () => {
    describe('WatchedEpisode Management Delete Component', () => {
        let comp: WatchedEpisodeDeleteDialogComponent;
        let fixture: ComponentFixture<WatchedEpisodeDeleteDialogComponent>;
        let service: WatchedEpisodeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SeriesappTestModule],
                declarations: [WatchedEpisodeDeleteDialogComponent]
            })
                .overrideTemplate(WatchedEpisodeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WatchedEpisodeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WatchedEpisodeService);
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

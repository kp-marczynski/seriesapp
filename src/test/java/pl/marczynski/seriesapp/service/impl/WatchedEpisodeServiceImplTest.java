package pl.marczynski.seriesapp.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import pl.marczynski.seriesapp.domain.*;
import pl.marczynski.seriesapp.domain.builder.FollowedSeriesBuilder;
import pl.marczynski.seriesapp.domain.builder.WatchedEpisodeBuilder;
import pl.marczynski.seriesapp.repository.WatchedEpisodeRepository;
import pl.marczynski.seriesapp.service.EpisodeService;
import pl.marczynski.seriesapp.service.FollowedSeriesService;
import pl.marczynski.seriesapp.service.UserService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class WatchedEpisodeServiceImplTest {

    @Mock
    private WatchedEpisodeRepository watchedEpisodeRepository;

    @Mock
    private FollowedSeriesService followedSeriesService;

    @Mock
    private UserService userService;

    @Mock
    private EpisodeService episodeService;

    @InjectMocks
    private WatchedEpisodeServiceImpl watchedEpisodeService;

    private static final long DEFAULT_ID = 1L;

    @Before
    public void setup() {
        ArgumentCaptor<WatchedEpisode> watchedEpisode = ArgumentCaptor.forClass(WatchedEpisode.class);
        when(watchedEpisodeRepository.save(watchedEpisode.capture())).thenAnswer(
            (Answer) invocation -> {
                WatchedEpisode captured = watchedEpisode.getValue();
                if (captured == null) return null;

                if (captured.getId() == null) {
                    captured.setId(DEFAULT_ID);
                }
                return captured;
            });
    }

    // ******************************************** Tests for save method ********************************************

    @Test
    public void whenLoggedUserNotPresentThenSaveResultIsNull() {
        //given
        Episode episode = new Episode();
        episode.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = new WatchedEpisodeBuilder().episode(episode).build();

        when(userService.findCurrentUser()).thenReturn(Optional.empty());

        //when
        WatchedEpisode result = watchedEpisodeService.save(watchedEpisode);

        //then
        assertNull(result);
    }

    @Test
    public void whenLoggedUserIsPresentThenSaveWatchedEpisodeInRepository() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = createWatchedEpisode();
        ArgumentCaptor<WatchedEpisode> watchedEpisodeCaptor = ArgumentCaptor.forClass(WatchedEpisode.class);

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));

        //when
        watchedEpisodeService.save(watchedEpisode);

        //then
        verify(watchedEpisodeRepository, times(1)).save(watchedEpisodeCaptor.capture());
        WatchedEpisode result = watchedEpisodeCaptor.getValue();
        assertThat(result.getUser(), is(user));
        assertThat(result.getEpisode(), is(watchedEpisode.getEpisode()));
        assertThat(result.getComment(), is(watchedEpisode.getComment()));
        assertThat(result.getRate(), is(watchedEpisode.getRate()));
    }

    @Test
    public void whenSavingWatchedEpisodeThenSetSeriesAsFollowed() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = createWatchedEpisode();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        ArgumentCaptor<FollowedSeries> capturedFollowed = ArgumentCaptor.forClass(FollowedSeries.class);
        FollowedSeries expected = new FollowedSeriesBuilder().series(watchedEpisode.getEpisode().getSeason().getSeries()).user(user).build();

        //when
        watchedEpisodeService.save(watchedEpisode);

        //then
        verify(followedSeriesService, times(1)).save(capturedFollowed.capture());
        FollowedSeries result = capturedFollowed.getValue();
        assertThat(result.getUser(), is(expected.getUser()));
        assertThat(result.getSeries(), is(expected.getSeries()));
    }

    @Test
    public void whenSavingWatchedEpisodeAndIdIsPresentThenIdShouldRemain() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = createWatchedEpisode();

        watchedEpisode.setId(DEFAULT_ID);
        when(userService.findCurrentUser()).thenReturn(Optional.of(user));

        //when
        WatchedEpisode result = watchedEpisodeService.save(watchedEpisode);

        //then
        assertThat(result.getId(), is(DEFAULT_ID));
    }

    @Test
    public void whenSavingWatchedEpisodeAndIdIsNotPresentThenIdShouldBeAdded() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = createWatchedEpisode();
        watchedEpisode.setId(null);
        when(userService.findCurrentUser()).thenReturn(Optional.of(user));

        //when
        WatchedEpisode result = watchedEpisodeService.save(watchedEpisode);

        //then
        assertNotNull(result.getId());
    }

    @Test
    public void whenSavingWatchedEpisodeThenCurrentUserShouldBeAddedToWatchedEpisode() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);
        WatchedEpisode watchedEpisode = createWatchedEpisode();
        watchedEpisode.setUser(null);
        when(userService.findCurrentUser()).thenReturn(Optional.of(user));

        //when
        WatchedEpisode result = watchedEpisodeService.save(watchedEpisode);

        //then
        assertThat(result.getUser(), is(user));
    }

    @Test
    public void whenSavingWatchedEpisodeAndWatchedEpisodeIsNullThenShouldReturnNull() {
        //given

        //when
        WatchedEpisode result = watchedEpisodeService.save(null);

        //then
        verify(episodeService, times(0)).findById(any());
        assertNull(result);
    }

    // ******************************************** Tests for getWatchedEpisodeForCurrentUserByEpisodeId method ********************************************

    @Test
    public void whenUserIsNotLoggedThenGetWatchedEpisodeForCurrentUserByEpisodeIdShouldReturnEmptyOptional() {
        //given
        when(userService.findCurrentUser()).thenReturn(Optional.empty());

        //when
        Optional<WatchedEpisode> result = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(DEFAULT_ID);

        //then
        verify(userService, times(1)).findCurrentUser();
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void whenWatchedEpisodeFoundForCurrentUserThenReturnIt() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);

        WatchedEpisode watchedEpisode = createWatchedEpisode();
        watchedEpisode.setUser(user);
        watchedEpisode.setId(DEFAULT_ID);

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(watchedEpisodeRepository.findByEpisodeIdAndUserIsCurrentUser(watchedEpisode.getId())).thenReturn(Optional.of(watchedEpisode));

        //when
        Optional<WatchedEpisode> result = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(DEFAULT_ID);

        //then
        verify(watchedEpisodeRepository, times(1)).findByEpisodeIdAndUserIsCurrentUser(watchedEpisode.getId());
        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(watchedEpisode));
    }

    @Test
    public void whenWatchedEpisodeNotFoundForCurrentUserAndEpisodeNotFoundThenReturnEmptyOptional() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);

        WatchedEpisode watchedEpisode = createWatchedEpisode();
        watchedEpisode.setUser(user);
        watchedEpisode.setId(DEFAULT_ID);

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(watchedEpisodeRepository.findByEpisodeIdAndUserIsCurrentUser(any())).thenReturn(Optional.empty());
        when(episodeService.findById(any())).thenReturn(Optional.empty());

        //when
        Optional<WatchedEpisode> result = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(DEFAULT_ID);

        //then
        verify(episodeService, times(1)).findById(DEFAULT_ID);
        assertThat(result.isPresent(), is(false));
    }

    @Test
    public void whenWatchedEpisodeNotFoundForCurrentUserAndEpisodeWasFoundThenReturnNewWatchedEpisode() {
        //given
        User user = new User();
        user.setId(DEFAULT_ID);

        Episode episode = createWatchedEpisode().getEpisode();

        when(userService.findCurrentUser()).thenReturn(Optional.of(user));
        when(watchedEpisodeRepository.findByEpisodeIdAndUserIsCurrentUser(any())).thenReturn(Optional.empty());
        when(episodeService.findById(any())).thenReturn(Optional.of(episode));

        //when
        Optional<WatchedEpisode> result = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(DEFAULT_ID);

        //then
        verify(episodeService, times(1)).findById(DEFAULT_ID);
        assertThat(result.isPresent(), is(true));
        WatchedEpisode watchedEpisodeResult = result.get();

        assertThat(watchedEpisodeResult.getEpisode(), is(episode));
        assertThat(watchedEpisodeResult.getUser(), is(user));
        assertNull(watchedEpisodeResult.getId());
        assertNull(watchedEpisodeResult.getComment());
        assertNull(watchedEpisodeResult.getRate());
    }

    @Test
    public void whenPassedNullParameterThenReturnEmptyOptional() {
        //given

        //when
        Optional<WatchedEpisode> result = watchedEpisodeService.getWatchedEpisodeForCurrentUserByEpisodeId(null);

        //then
        verify(episodeService, times(0)).findById(any());
        assertThat(result.isPresent(), is(false));
    }

    // *********************************************

    private WatchedEpisode createWatchedEpisode() {
        Series series = new Series();
        series.setId(DEFAULT_ID);
        series.setName("Sherlock");
        series.setReleaseYear(2014);
        series.setDescription("no shit Sherlock");
        Season season = new Season();
        series.addSeason(season);
        season.setSeries(series);
        season.setId(DEFAULT_ID);
        season.setNumber(1);
        season.setReleaseYear(2014);
        season.setDescription("descrip");
        Episode episode = new Episode();
        season.addEpisode(episode);
        episode.setSeason(season);
        episode.setId(DEFAULT_ID);
        episode.setNumber(1);
        episode.setDuration(20);
        episode.setTitle("Odc 1");
        return new WatchedEpisodeBuilder().episode(episode).build();
    }
}

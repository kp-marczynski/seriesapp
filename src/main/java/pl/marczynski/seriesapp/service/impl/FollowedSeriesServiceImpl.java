package pl.marczynski.seriesapp.service.impl;

import org.springframework.stereotype.Service;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.builder.FollowedSeriesBuilder;
import pl.marczynski.seriesapp.repository.FollowedSeriesRepository;
import pl.marczynski.seriesapp.security.AuthoritiesConstants;
import pl.marczynski.seriesapp.security.SecurityUtils;
import pl.marczynski.seriesapp.service.FollowedSeriesService;
import pl.marczynski.seriesapp.service.SeriesService;
import pl.marczynski.seriesapp.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class FollowedSeriesServiceImpl implements FollowedSeriesService {
    private FollowedSeriesRepository followedSeriesRepository;

    private UserService userService;
    private SeriesService seriesService;

    public FollowedSeriesServiceImpl(FollowedSeriesRepository FollowedSeriesRepository, UserService userService, SeriesService seriesService) {
        this.followedSeriesRepository = FollowedSeriesRepository;
        this.userService = userService;
        this.seriesService = seriesService;
    }

    /**
     * Save FollowedSeries
     * @param followedSeries followedSeries to save
     * @return FollowedSerie
     */
    @Override
    public FollowedSeries save(FollowedSeries followedSeries) {
        FollowedSeries result = null;
        Optional<User> user = userService.findCurrentUser();
        Optional<FollowedSeries> followedSeriesOptional = followedSeriesRepository.findBySeriesIdAndUserIsCurrentUser(followedSeries.getSeries().getId());
        followedSeriesOptional.ifPresent(followedSeries1 -> followedSeries.setId(followedSeries1.getId()));
        if (user.isPresent()) {
            followedSeries.setUser(user.get());
            result = followedSeriesRepository.save(followedSeries);
        }

        return result;
    }

    /**
     * Find all FollowedSeries
     * @return List of FollowedSeries
     */
    @Override
    public List<FollowedSeries> findAll() {
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            return followedSeriesRepository.findAll();
        } else {
            return followedSeriesRepository.findByUserIsCurrentUser();
        }
    }

    /**
     * Find FollowedSeries by id
     * @param id the id of the FollowedSeries to find
     * @return Optional of FollowedSeries
     */
    @Override
    public Optional<FollowedSeries> findById(Long id) {
        return followedSeriesRepository.findById(id);
    }

    /**
     * Delete FollowedSeries by id
     * @param id the id of the FollowedSeries to delete
     */
    @Override
    public void deleteById(Long id) {
        followedSeriesRepository.deleteById(id);
    }

    /**
     * Update an existing followedSeries
     *
     * @param followedSeries followedSeries to update
     * @return FollowedSeries
     */
    @Override
    public FollowedSeries update(FollowedSeries followedSeries) {
        return save(followedSeries);
    }

    /**
     * Get an average rate for series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Float
     */
    @Override
    public Float getAverageRate(Long seriesId) {
        return followedSeriesRepository.getAverageRateBySeriesId(seriesId);
    }

    /**
     * Get a count of how many users are following series, which id is seriesId
     * @param seriesId the id of the Series to check
     * @return Integer
     */
    @Override
    public Integer getRateCount(Long seriesId) {
        return followedSeriesRepository.getRateCountBySeriesId(seriesId);
    }

    /**
     * Find if current user is following series, which id is id
     * @param id the id of the Series to check
     * @return Optional of FollowedSeries
     */
    @Override
    public Optional<FollowedSeries> findFollowedBySeriesId(Long id) {
        Optional<FollowedSeries> result;
        result = followedSeriesRepository.findBySeriesIdAndUserIsCurrentUser(id);
        if (!result.isPresent()) {
            Optional<User> user = userService.findCurrentUser();
            Optional<Series> series = seriesService.findById(id);
            if (user.isPresent() && series.isPresent()) {
                result = Optional.of(new FollowedSeriesBuilder().user(user.get()).series(series.get()).build());
            }
        }
        return result;
    }
}

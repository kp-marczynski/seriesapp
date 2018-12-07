package pl.marczynski.seriesapp.domain.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.marczynski.seriesapp.domain.FollowedSeries;
import pl.marczynski.seriesapp.domain.Series;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.enumeration.Rate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class FollowedSeriesBuilder {

    private Rate rate;
    private String comment;
    private User user;
    private Series series;

    public FollowedSeries build() {
        return new FollowedSeries(rate, comment, user, series);
    }

    public FollowedSeriesBuilder rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public FollowedSeriesBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public FollowedSeriesBuilder user(User user) {
        this.user = user;
        return this;
    }

    public FollowedSeriesBuilder series(Series series) {
        this.series = series;
        return this;
    }

}

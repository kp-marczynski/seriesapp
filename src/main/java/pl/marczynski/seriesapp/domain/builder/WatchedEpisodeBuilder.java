package pl.marczynski.seriesapp.domain.builder;

import pl.marczynski.seriesapp.domain.Episode;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.domain.WatchedEpisode;
import pl.marczynski.seriesapp.domain.enumeration.Rate;

public class WatchedEpisodeBuilder {

    private Rate rate;
    private String comment;
    private User user;
    private Episode episode;

    public WatchedEpisode build() {
        return new WatchedEpisode(rate, comment, user, episode);
    }

    public WatchedEpisodeBuilder rate(Rate rate) {
        this.rate = rate;
        return this;
    }

    public WatchedEpisodeBuilder comment(String comment) {
        this.comment = comment;
        return this;
    }

    public WatchedEpisodeBuilder user(User user) {
        this.user = user;
        return this;
    }

    public WatchedEpisodeBuilder episode(Episode episode) {
        this.episode = episode;
        return this;
    }
}

CREATE TABLE `User` (
    id LONG NOT NULL AUTO_INCREMENT,
    login STRING NOT NULL UNIQUE,
    password STRING NOT NULL,
    email STRING NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Series (
    id          LONG    NOT NULL AUTO_INCREMENT,
    name STRING NOT NULL,
    releaseYear INTEGER NOT NULL,
    description STRING,
    PRIMARY KEY (id)
);
CREATE TABLE Rate (
    rate STRING NOT NULL,
    PRIMARY KEY (rate)
);
CREATE TABLE FollowedSeries (
    id       LONG NOT NULL AUTO_INCREMENT,
    rate STRING,
    comment STRING,
    userId   LONG NOT NULL,
    seriesId LONG NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Season (
    id          LONG    NOT NULL AUTO_INCREMENT,
    number      INTEGER NOT NULL,
    description STRING,
    releaseYear INTEGER NOT NULL,
    seriesId    LONG    NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Episode (
    id       LONG    NOT NULL AUTO_INCREMENT,
    number   INTEGER NOT NULL,
    title STRING NOT NULL,
    releaseDate LocalDate NULL,
    duration INTEGER,
    seasonId LONG    NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE WatchedEpisode (
    id        LONG NOT NULL AUTO_INCREMENT,
    rate STRING,
    comment STRING,
    userId    LONG NOT NULL,
    episodeId LONG NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE FollowedSeries
    ADD CONSTRAINT FKFollowedSe939963 FOREIGN KEY (seriesId) REFERENCES Series (id);
ALTER TABLE FollowedSeries
    ADD CONSTRAINT FKFollowedSe427625 FOREIGN KEY (userId) REFERENCES `User` (id);
ALTER TABLE FollowedSeries
    ADD CONSTRAINT FKFollowedSe335976 FOREIGN KEY (rate) REFERENCES Rate (rate);
ALTER TABLE Season
    ADD CONSTRAINT FKSeason631368 FOREIGN KEY (seriesId) REFERENCES Series (id);
ALTER TABLE Episode
    ADD CONSTRAINT FKEpisode319189 FOREIGN KEY (seasonId) REFERENCES Season (id);
ALTER TABLE WatchedEpisode
    ADD CONSTRAINT FKWatchedEpi388034 FOREIGN KEY (episodeId) REFERENCES Episode (id);
ALTER TABLE WatchedEpisode
    ADD CONSTRAINT FKWatchedEpi254322 FOREIGN KEY (userId) REFERENCES `User` (id);
ALTER TABLE WatchedEpisode
    ADD CONSTRAINT FKWatchedEpi808917 FOREIGN KEY (rate) REFERENCES Rate (rate);

CREATE TABLE `User` (
    id LONG NOT NULL AUTO_INCREMENT,
    login VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
CREATE TABLE Series (
    id          LONG    NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    releaseYear INTEGER NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id)
);
CREATE TABLE Rate (
    rate VARCHAR(255) NOT NULL,
    PRIMARY KEY (rate)
);
CREATE TABLE FollowedSeries (
    id       LONG NOT NULL AUTO_INCREMENT,
    rate VARCHAR(255),
    comment VARCHAR(255),
    userId   LONG NOT NULL,
    seriesId LONG NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Season (
    id          LONG    NOT NULL AUTO_INCREMENT,
    number      INTEGER NOT NULL,
    description VARCHAR(255),
    releaseYear INTEGER NOT NULL,
    seriesId    LONG    NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE Episode (
    id       LONG    NOT NULL AUTO_INCREMENT,
    number   INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    releaseDate LocalDate NULL,
    duration INTEGER,
    seasonId LONG    NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE WatchedEpisode (
    id        LONG NOT NULL AUTO_INCREMENT,
    rate VARCHAR(255),
    comment VARCHAR(255),
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

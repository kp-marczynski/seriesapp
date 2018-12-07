package pl.marczynski.seriesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import pl.marczynski.seriesapp.domain.enumeration.Rate;

/**
 * A FollowedSeries.
 */
@Entity
@Table(name = "followed_series")
public class FollowedSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "rate")
    private Rate rate;

    @Size(max = 500)
    @Column(name = "jhi_comment", length = 500)
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Series series;

    public FollowedSeries() {
    }

    public FollowedSeries(Rate rate, String comment, User user, Series series) {
        this.rate = rate;
        this.comment = comment;
        this.user = user;
        this.series = series;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FollowedSeries followedSeries = (FollowedSeries) o;
        if (followedSeries.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), followedSeries.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FollowedSeries{" +
            "id=" + getId() +
            ", rate='" + getRate() + "'" +
            ", comment='" + getComment() + "'" +
            "}";
    }
}

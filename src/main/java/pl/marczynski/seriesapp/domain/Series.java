package pl.marczynski.seriesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Series.
 */
@Entity
@Table(name = "series")
public class Series implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @NotNull
    @Min(value = 1926)
    @Column(name = "release_year", nullable = false)
    private Integer releaseYear;

    @Size(max = 1000)
    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER)
    private Set<Season> seasons = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Series name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public Series releaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public Series description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Season> getSeasons() {
        return seasons;
    }

    public Series seasons(Set<Season> seasons) {
        this.seasons = seasons;
        return this;
    }

    public Series addSeason(Season season) {
        this.seasons.add(season);
        season.setSeries(this);
        return this;
    }

    public Series removeSeason(Season season) {
        this.seasons.remove(season);
        season.setSeries(null);
        return this;
    }

    public void setSeasons(Set<Season> seasons) {
        this.seasons = seasons;
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
        Series series = (Series) o;
        if (series.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), series.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Series{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", releaseYear=" + getReleaseYear() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}

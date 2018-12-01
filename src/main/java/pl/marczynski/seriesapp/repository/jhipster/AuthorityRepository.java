package pl.marczynski.seriesapp.repository.jhipster;

import pl.marczynski.seriesapp.domain.jhipster.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

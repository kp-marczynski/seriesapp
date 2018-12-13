package pl.marczynski.seriesapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.marczynski.seriesapp.domain.User;
import pl.marczynski.seriesapp.service.jhipster.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> activateRegistration(String key);

    Optional<User> completePasswordReset(String newPassword, String key);

    Optional<User> requestPasswordReset(String mail);

    User registerUser(UserDTO userDTO, String password);

    User createUser(UserDTO userDTO);

    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl);

    Optional<UserDTO> updateUser(UserDTO userDTO);

    void deleteUser(String login);

    void changePassword(String currentClearTextPassword, String newPassword);


    Page<UserDTO> getAllManagedUsers(Pageable pageable);


    Optional<User> getUserWithAuthoritiesByLogin(String login);


    Optional<User> getUserWithAuthorities(Long id);


    Optional<User> getUserWithAuthorities();

    void removeNotActivatedUsers();

    List<String> getAuthorities();

    Optional<User> findCurrentUser();
}

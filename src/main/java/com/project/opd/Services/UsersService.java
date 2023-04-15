package com.project.opd.Services;

import com.project.opd.Database.Users;
import com.project.opd.Errors.Failed;
import com.project.opd.Repository.UsersCrudRepository;
import com.project.opd.Requests.LoginRequest;
import com.project.opd.Requests.RegistrationRequest;
import com.project.opd.Response.InfoResponse;
import com.project.opd.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class UsersService {
    @Autowired
    private UsersCrudRepository usersCrudRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registration(RegistrationRequest registrationRequest) {
        if (usersCrudRepository.findByLogin(registrationRequest.getLogin()).isPresent()) {
            throw new Failed("Such login is exists");
        }
        Users users = Users.builder().login(registrationRequest.getLogin()).isAdmin(false)
                .passwordHash(passwordEncoder.encode(registrationRequest.getPassword())).build();
        usersCrudRepository.save(users);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Users> optionalUser = usersCrudRepository.findByLogin(loginRequest.getLogin());
        if (optionalUser.isEmpty()) {
            throw new Failed("Hasn't such login");
        }
        Users users = optionalUser.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), users.getPasswordHash())) {
            return new LoginResponse(null);
        }
        return new LoginResponse(users.getId());
    }

    @Transactional(readOnly = true)
    public InfoResponse getInfo(Long id) {
        Optional<Users> optionalUsers = usersCrudRepository.findById(id);
        if (optionalUsers.isEmpty()) {
            throw new Failed("Bad id");
        }
        Users user = optionalUsers.get();
        return InfoResponse.builder().isAdmin(user.getIsAdmin()).login(user.getLogin()).build();
    }
}

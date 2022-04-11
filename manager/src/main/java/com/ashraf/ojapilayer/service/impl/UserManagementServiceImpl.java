package com.ashraf.ojapilayer.service.impl;

import com.ashraf.ojapilayer.api.requestmodels.LoginRequest;
import com.ashraf.ojapilayer.entity.RegisteredUser;
import com.ashraf.ojapilayer.entity.UserProfile;
import com.ashraf.ojapilayer.exception.DuplicateUserException;
import com.ashraf.ojapilayer.exception.InvalidPasswordException;
import com.ashraf.ojapilayer.api.requestmodels.UserCreationRequest;
import com.ashraf.ojapilayer.exception.UnRegisteredUserException;
import com.ashraf.ojapilayer.repository.RegisteredUserRepository;
import com.ashraf.ojapilayer.repository.UserProfileRepository;
import com.ashraf.ojapilayer.service.UserManagementService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Log4j2
public class UserManagementServiceImpl implements UserManagementService {

    private final RegisteredUserRepository registeredUserRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    @Transactional
    public void createUser(final UserCreationRequest userCreationRequest) {
        if (registeredUserRepository.existsById(userCreationRequest.getId()) || !isValidPassword(userCreationRequest.getPassword()))  {
            throw new DuplicateUserException(String.format("id %s is already taken, please use another id", userCreationRequest.getId()));
        }
        final RegisteredUser registeredUser = RegisteredUser.builder().password(userCreationRequest.getPassword()).id(userCreationRequest.getId()).build();
        final UserProfile userProfile = UserProfile.builder().build();
        userProfile.addRegisteredUser(registeredUser);
        registeredUserRepository.save(registeredUser);
    }

    @Override
    public void verifyLogin(LoginRequest loginRequest) {
        RegisteredUser registeredUser = registeredUserRepository.findById(loginRequest.getId()).orElse(null);
        if (Objects.isNull(registeredUser)) {
            throw new UnRegisteredUserException("User is not registered, please create account first");
        }
        if (!registeredUser.getPassword().equals(loginRequest.getPassword())) {
            throw new InvalidPasswordException("password or username is invalid, please enter correct password");
        }
        log.info("login successful for user with id {}", loginRequest.getId());
    }

    @Override
    public List<UserProfile> findAllUserById(List<String> ids) {
        List<Long> userProfileIds = registeredUserRepository.findAllUserProfileIdByHandle(ids);
        return userProfileRepository.findAllByIdIn(userProfileIds);
    }

    // can add more robust checks
    private boolean isValidPassword(final String password) {
        if (StringUtils.isNotEmpty(password)) return true;
        throw new InvalidPasswordException("Please enter a valid password");
    }
}

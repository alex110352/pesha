package com.example.pesha.service;

import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public User createUser(User requestUser) {

        boolean isDuplicate = userRepository.findByUserName(requestUser.getUserName()).isPresent();
        if (isDuplicate) {
            throw new DuplicateException(requestUser.getUserName() + " is duplicate");
        }

        User user = new User();
        user.setUserName(requestUser.getUserName());
        user.setUserPassword(requestUser.getUserPassword());
        user.setAuthorities(requestUser.getAuthorities());
        return userRepository.save(user);
    }

    public User getUser(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("can't find " + userName));
    }

    public List<User> getAllUser() {
        if (userRepository.findAll().isEmpty()) {
            throw new NotFoundException("user list is empty");
        }
        return userRepository.findAll();
    }

    public User replaceUser(String userName, String userPassword, User requestUser) {
        User replaceUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("can't find " + userName));

        if (!Objects.equals(replaceUser.getUserPassword(), userPassword)) {
            throw new RuntimeException("password is wrong");
        }

        replaceUser.setUserName(requestUser.getUserName());
        replaceUser.setUserPassword(requestUser.getUserPassword());
        replaceUser.setAuthorities(requestUser.getAuthorities());

        return userRepository.save(replaceUser);
    }

    public void deleteUser(String userName) {
        User user = userRepository
                .findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("can't find " + userName));
        userRepository.delete(user);
    }
}

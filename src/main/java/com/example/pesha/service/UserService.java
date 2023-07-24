package com.example.pesha.service;

import com.example.pesha.dao.entity.User;
import com.example.pesha.dao.repositories.UserRepository;
import com.example.pesha.exception.DuplicateException;
import com.example.pesha.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
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

            User user = userRepository.findByUserName(username)
                    .orElseThrow(() ->new UsernameNotFoundException("can't find user"));

            Collection<? extends GrantedAuthority> authorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .toList();

            return new org.springframework.security.core.userdetails.User(
                    username,
                    user.getUserPassword(),
                    authorities);

        }

    public User createUser(User requestUser) {

        boolean isDuplicate = userRepository.findByUserName(requestUser.getUserName()).isPresent();
        if (isDuplicate) {
            throw new DuplicateException(requestUser.getUserName() + " is duplicate");
        }

        PasswordEncoder pe = new BCryptPasswordEncoder();

        User user = new User();
        user.setUserName(requestUser.getUserName());
        user.setUserPassword(pe.encode(requestUser.getUserPassword()));
        user.setAuthorities(requestUser.getAuthorities());
        return userRepository.save(user);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("can't find " + userId));
    }

    public List<User> getAllUser() {
        if (userRepository.findAll().isEmpty()) {
            throw new NotFoundException("user list is empty");
        }
        return userRepository.findAll();
    }

    public User replaceUser(Long userId, User requestUser) {
        User replaceUser = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("can't find " + userId));

        replaceUser.setUserName(requestUser.getUserName());
        replaceUser.setUserPassword(requestUser.getUserPassword());
        replaceUser.setAuthorities(requestUser.getAuthorities());

        return userRepository.save(replaceUser);
    }

    public void deleteUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NotFoundException("can't find " + userId));
        userRepository.delete(user);
    }
}

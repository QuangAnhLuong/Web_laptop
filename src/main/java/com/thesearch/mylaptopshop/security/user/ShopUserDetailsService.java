package com.thesearch.mylaptopshop.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thesearch.mylaptopshop.model.User;
import com.thesearch.mylaptopshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email)throws UsernameNotFoundException{
        User user = Optional.ofNullable(userRepository.findByEmail(email))
            .orElseThrow(()-> new UsernameNotFoundException("User Not Found!"));
        return ShopUserDetails.buildUserDetails(user);
    }
}

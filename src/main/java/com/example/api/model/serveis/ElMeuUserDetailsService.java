package com.example.api.model.serveis;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElMeuUserDetailsService implements UserDetailsService {

    private final ServicioUsuario servicioUsuarioisUserDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return servicioUsuarioisUserDetails.consultarPorUsername(username);
    }

}

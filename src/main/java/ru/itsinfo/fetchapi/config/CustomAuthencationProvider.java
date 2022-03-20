package ru.itsinfo.fetchapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itsinfo.fetchapi.model.User;
import ru.itsinfo.fetchapi.repository.UserRepository;

@Component
public class CustomAuthencationProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication)
                                          throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();
        //получаем пользователя
        User myUser = dao.findByEmail(userName).get();
        if (!passwordEncoder.matches(password, myUser.getPassword())) {
            throw new BadCredentialsException("Bad password " + password);
        }
        UserDetails principal = new User(
                myUser.getFirstName(),
                myUser.getLastName(),
                myUser.getAge(),
                myUser.getEmail(),
                myUser.getPassword(),
                myUser.getAuthorities());
        return new UsernamePasswordAuthenticationToken(
                principal, password, principal.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
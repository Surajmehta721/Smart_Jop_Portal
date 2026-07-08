package jobs.com.SmartJobPortal.security;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    CostomDetailsService costomDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name=authentication.getName();
        String password=authentication.getCredentials().toString();
        UserDetails userDetails=costomDetailsService.loadUserByUsername(name);
        if(passwordEncoder.matches(password,userDetails.getPassword()))
        {
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(),password,userDetails.getAuthorities());
        }
        else {
            throw new BadCredentialsException("Invaild User");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);

    }
}

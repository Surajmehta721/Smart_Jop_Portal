package jobs.com.SmartJobPortal.security;

import jobs.com.SmartJobPortal.UserRepo;
import jobs.com.SmartJobPortal.entity.JobPortalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CostomDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JobPortalUser> user_opt=userRepo.findByUsername(username);
        if(user_opt.isEmpty())
            user_opt=userRepo.findByEmail(username);
        JobPortalUser user=user_opt.orElseThrow(()->new UsernameNotFoundException("User not Exist"));
        List<GrantedAuthority> authorities= List.of(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));

      return new User(user.getUsername(), user.getPassword(),authorities);
    }
}

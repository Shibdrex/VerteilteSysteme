package dh.distributed.systems.Server.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dh.distributed.systems.Server.model.ListUser;
import dh.distributed.systems.Server.model.ListUserDetails;

@Service
public class ListUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;

    public ListUserDetailsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${user-service.url}")
    private String userServiceUrl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            ListUser user = restTemplate.getForObject(userServiceUrl + "/email?email=" + email, ListUser.class);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
            return new ListUserDetails(user);
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with email: " + email, e);
        }
    }
}

package worksBeyond.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import worksBeyond.neo4jRepository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		
		try{
			worksBeyond.model.User domainUser = userRepository.findByEmail(email);
			
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
		
			return new User(
					domainUser.getEmail(), 
					domainUser.getPassword(),
					enabled,
					accountNonExpired,
					credentialsNonExpired,
					accountNonLocked,
					getAuthorities(domainUser.getRole().toString()));
					
					
					
			
		} catch (Exception e){
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(String role){
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority(role.toString()));
		return authList;
	}
	
	
	

}

package com.versionsystem.basic.security;

import com.versionsystem.persistence.model.MemberLogin;
import com.versionsystem.basic.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A custom {@link UserDetailsService} where user information is retrieved from
 * a JPA repository
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpServletRequest request;

	/**
	 * 去DB查User
	 * ps. 这的里的exception的message无法传递到前台
	 */
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		try {
			MemberLogin domainUser = userRepository.findByUserId(userId);
			if (domainUser == null) {
				throw new RuntimeException("InvalidUser");
			}
			boolean enabled = true;
			boolean accountNonExpired = true;
			boolean credentialsNonExpired = true;
			boolean accountNonLocked = true;
			// ServletRequestAttributes attr = (ServletRequestAttributes)
			// RequestContextHolder.currentRequestAttributes();
			request.getSession(true); // true
			User signedUser = new User(domainUser.getUserId(), domainUser.getPassword(), enabled, accountNonExpired,
					credentialsNonExpired, accountNonLocked, getAuthorities(1));
			return signedUser;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * Retrieves a collection of {@link GrantedAuthority} based on a numerical
	 * role
	 *
	 * @param role the numerical role
	 * @return a collection of {@link GrantedAuthority
	 */
	public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
		List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
		return authList;
	}

	/**
	 * Converts a numerical role to an equivalent list of roles
	 *
	 * @param role the numerical role
	 * @return list of roles as as a list of {@link String}
	 */
	public List<String> getRoles(Integer role) {
		List<String> roles = new ArrayList<String>();

		if (role.intValue() == 1) {
			roles.add("ROLE_USER");
			roles.add("ROLE_ADMIN");

		} else if (role.intValue() == 2) {
			roles.add("ROLE_USER");
		}

		return roles;
	}

	/**
	 * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
	 *
	 * @param roles {@link String} of roles
	 * @return list of granted authorities
	 */
	public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}
}

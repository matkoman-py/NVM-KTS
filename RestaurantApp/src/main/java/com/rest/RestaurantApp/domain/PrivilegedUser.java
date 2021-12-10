package com.rest.RestaurantApp.domain;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import com.rest.RestaurantApp.domain.enums.PrivilegedUserType;
import com.rest.RestaurantApp.domain.enums.UserType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@DiscriminatorValue("PRIVILEGED_USER")
@Where(clause = "deleted = false")
@Getter
@Setter
@NoArgsConstructor
public class PrivilegedUser extends User implements UserDetails {
	
	//@Column(nullable = false)
	private String username;
	
	//@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	//@Column(nullable = false)
	private PrivilegedUserType privilegedType;

	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}


	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
			inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles;

	public PrivilegedUser(String email, String name, String surname, Date birthday, UserType type, String username,
			String password, PrivilegedUserType privilegedType) {
		super(email, name, surname, birthday, type);
		this.username = username;
		this.password = password;
		this.privilegedType = privilegedType;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !this.deleted;
	}

}

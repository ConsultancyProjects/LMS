package com.lms.tutor.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

	private static final long serialVersionUID = 4294454865198678177L;
	private String userName;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public MyUserDetails(User user) {
        this.userName = user.getUserId();
        this.password = user.getPassword();
        this.active = true;
        String role = "";
        Optional<Role> roleData = Optional.ofNullable(user.getRole());
        if (roleData.isPresent()) role = roleData.get().getName();
        this.authorities.add(new SimpleGrantedAuthority(role));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }
}
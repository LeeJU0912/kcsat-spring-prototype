package hpclab.ksatengmaker_spring.security;

import hpclab.ksatengmaker_spring.community.domain.Member;
import hpclab.ksatengmaker_spring.community.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static hpclab.ksatengmaker_spring.community.domain.Role.*;

public class CustomUserDetails implements UserDetails {
    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    private GrantedAuthority getAuthority(Role role) {
        return new SimpleGrantedAuthority(role.toString());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();

        switch (member.getRole()) {
            case ROLE_ADMIN : authorityList.add(getAuthority(ROLE_ADMIN));
            case ROLE_MANAGER : authorityList.add(getAuthority(ROLE_MANAGER));
            case ROLE_USER : authorityList.add(getAuthority(ROLE_USER));
        }

        return authorityList;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    public String getUsername() {
        return member.getUsername();
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
        return true;
    }
}

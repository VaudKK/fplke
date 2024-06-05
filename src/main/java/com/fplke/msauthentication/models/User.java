package com.fplke.msauthentication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fplke.msauthentication.dto.request.CreateUserDto;
import com.fplke.msauthentication.models.audit.Audit;
import com.fplke.msauthentication.utils.Constants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Table(name = "fpl_users")
@Entity
@Getter
public class User extends Audit implements UserDetails {
    @Id
    private String userId;
    private Long teamId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String roles;
    private Boolean enabled;
    private Boolean credentialExpired;
    private Boolean accountLocked;
    @JsonIgnore
    private String userPass;
    @Setter
    @JsonIgnore
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(roles.split(","));
    }

    @Override
    public String getPassword() {
        return this.getUserPass();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return getAccountLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return getCredentialExpired();
    }

    @Override
    public boolean isEnabled() {
        return getEnabled();
    }

    public User buildNewUser(CreateUserDto createUserDto){
        this.userId = UUID.randomUUID().toString().replace("-","");
        this.email = createUserDto.email();
        this.userPass = createUserDto.password();
        this.username = createUserDto.username();
        this.teamId = createUserDto.teamId();
        this.enabled = true;
        this.credentialExpired = false;
        this.accountLocked = false;
        this.roles = Constants.Roles.ROLE_USER;

        return this;
    }

}

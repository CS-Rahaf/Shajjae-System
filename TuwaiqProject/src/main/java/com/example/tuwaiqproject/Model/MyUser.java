package com.example.tuwaiqproject.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MyUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The username should not be empty")
    @Size(min = 4,max = 15, message = "Username between 4 and 15")
    @Column(unique = true, columnDefinition = "varchar(15) not null")
    private String username;

    @NotEmpty(message = "The password should not be empty")
  /*  @Size(min = 8,max = 20, message = "password should be between 8 to 20 characters")*/
    private String password;

    @NotEmpty
    @Pattern(regexp="(SUPER|STADIUM|FAN)", message = "The role should be either Admin or fan")
    @Column(columnDefinition = "varchar(7) not null check (role='SUPER' or role= 'STADIUM' or role= 'FAN')")
    private String role;


    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private StadiumAdmin stadiumAdmin;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "myUser")
    @PrimaryKeyJoinColumn
    private FanUser fanUser;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
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


    @Override
    public String toString() {
        return "MyUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

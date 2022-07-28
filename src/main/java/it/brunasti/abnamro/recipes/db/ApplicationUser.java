package it.brunasti.abnamro.recipes.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class ApplicationUser implements UserDetails  {

    private @Id String id;
    private String username;
    private String password;

    protected ApplicationUser() {}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof ApplicationUser))
            return false;
        ApplicationUser applicationUser = (ApplicationUser) o;
        return Objects.equals(this.id, applicationUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // JWT and Spring Boot Authentication part

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

}

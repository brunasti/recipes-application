package it.brunasti.abnamro.recipes.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class ApplicationUser {

    private @Id @GeneratedValue Long id;
    private String name;

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

}

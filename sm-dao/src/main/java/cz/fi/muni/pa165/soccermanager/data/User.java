package cz.fi.muni.pa165.soccermanager.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Entity representing user of system.
 *
 * @author Lenka Horvathova
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String userName;

    @NotNull
    @Column(nullable = false)
    private String passwordHash;

    @Column(columnDefinition = "boolean default false")
    private boolean admin;

    @OneToOne
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (!(o instanceof User)) {
            return false;
        }

        final User other = (User) o;

        return Objects.equals(this.getUserName(), other.getUserName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((this.getUserName() == null) ? 0 : this.getUserName().hashCode());

        return result;
    }

    @Override
    public String toString() {
        return "User: { name: " + getUserName() + ", isAdmin: " + isAdmin() + " }";
    }
}

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String userName;

    @NotNull
    @Column(nullable = false)
    private String passwordHash;

    @Column(columnDefinition = "boolean default false")
    private boolean admin;

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId()) &&
                Objects.equals(getUserName(), user.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override public String toString() {
        return "User: { name: " + getUserName() + ", isAdmin: " + isAdmin() + " }";
    }
}

package net.yixingong.dining.reviews.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<User> users = new HashSet<>();

    // Utility method to add user
    public void addUser(User user) {
        users.add(user);
        user.getRoles().add(this);
    }

    // Utility method to remove user
    public void removeUser(User user) {
        users.remove(user);
        user.getRoles().remove(this);
    }
}
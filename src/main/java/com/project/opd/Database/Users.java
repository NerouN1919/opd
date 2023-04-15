package com.project.opd.Database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_admin")
    private Boolean isAdmin;
    @Column(name = "login")
    private String login;
    @Column(name = "password_hash")
    private String passwordHash;
    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Groups> groups = new ArrayList<>();
    public void addGroup(Groups group){
        this.groups.add(group);
        group.getUsers().add(this);
    }
    public void removeGroup(Groups group){
        this.groups.remove(group);
        group.getUsers().remove(this);
    }
}

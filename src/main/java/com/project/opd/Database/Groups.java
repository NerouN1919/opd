package com.project.opd.Database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Groups {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "admin_id")
    private Long adminId;
    @ManyToMany
    @JoinTable(
            name = "user_group",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private List<Users> users = new ArrayList<>();
    @ManyToMany
    @JoinTable(name="project_group",
            joinColumns=  @JoinColumn(name="group_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="project_id", referencedColumnName="id") )
    private Set<Projects> projects = new HashSet<>();

    public void addUser(Users user){
        this.users.add(user);
        user.getGroups().add(this);
    }
    public void removeUser(Users user){
        this.users.remove(user);
        user.getGroups().remove(this);
    }
    public void addProject(Projects projects){
        this.projects.add(projects);
        projects.getGroups().add(this);
    }
}

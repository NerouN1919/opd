package com.project.opd.Database;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Projects {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "pathToLogo")
    private String pathToLogo;
    @Column(name = "createDate")
    private Date createDate;
    @Column(name = "changeDate")
    private Date changeDate;
    @Column(name = "pathToList")
    private String pathToList;
    @Column(name = "pathToTable")
    private String pathToTable;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private Users creator;
    @ManyToMany
    @JoinTable(name="project_group",
            joinColumns=  @JoinColumn(name="project_id", referencedColumnName="id"),
            inverseJoinColumns= @JoinColumn(name="group_id", referencedColumnName="id") )
    private Set<Groups> groups = new HashSet<>();
    public void addGroup(Groups group){
        this.groups.add(group);
    }
}

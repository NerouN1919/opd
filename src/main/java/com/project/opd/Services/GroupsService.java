package com.project.opd.Services;

import com.project.opd.Database.Groups;
import com.project.opd.Database.Users;
import com.project.opd.Errors.Failed;
import com.project.opd.Repository.GroupsCrudRepository;
import com.project.opd.Repository.UsersCrudRepository;
import com.project.opd.Response.CreateGroupResponse;
import com.project.opd.Response.GroupsResponse;
import com.project.opd.Response.UsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GroupsService {
    @Autowired
    private GroupsCrudRepository groupsCrudRepository;
    @Autowired
    private UsersCrudRepository usersCrudRepository;

    public CreateGroupResponse createGroup(Long adminId) {
        Optional<Users> optionalUser = usersCrudRepository.findById(adminId);
        if (optionalUser.isEmpty()) {
            throw new Failed("Bad admin id");
        }
        Users admin = optionalUser.get();
        Groups groups = new Groups();
        groups.setAdminId(adminId);
        groups.addUser(admin);
        groupsCrudRepository.save(groups);
        return new CreateGroupResponse(groups.getId());
    }

    public void addUserToGroup(Long groupId, Long userId) {
        Optional<Users> optionalUser = usersCrudRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Failed("Bad user id");
        }
        Optional<Groups> optionalGroups = groupsCrudRepository.findById(groupId);
        if (optionalGroups.isEmpty()) {
            throw new Failed("Bad group id");
        }
        Groups group = optionalGroups.get();
        Users user = optionalUser.get();
        group.addUser(user);
    }

    @Transactional(readOnly = true)
    public List<UsersResponse> getAllUsersForGroup(Long groupId) {
        Optional<Groups> optionalGroup = groupsCrudRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new Failed("Bad group id");
        }
        Groups group = optionalGroup.get();
        return group.getUsers().stream().map(user -> UsersResponse.builder().login(user.getLogin()).id(user.getId()).build()).toList();
    }

    @Transactional(readOnly = true)
    public List<GroupsResponse> getAllGroups() {
        List<Groups> groups = (List<Groups>) groupsCrudRepository.findAll();
        return groups.stream().map(group -> GroupsResponse.builder().adminId(group.getAdminId()).id(group.getId()).build()).toList();
    }

    @Transactional(readOnly = true)
    public GroupsResponse getGroupById(Long groupId) {
        Optional<Groups> optionalGroup = groupsCrudRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            throw new Failed("Bad group id");
        }
        Groups group = optionalGroup.get();
        return GroupsResponse.builder().id(groupId).adminId(group.getAdminId()).build();
    }
}

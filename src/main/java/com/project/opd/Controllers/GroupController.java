package com.project.opd.Controllers;

import com.project.opd.Response.CreateGroupResponse;
import com.project.opd.Response.GroupsResponse;
import com.project.opd.Response.UsersResponse;
import com.project.opd.Services.GroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {
    @Autowired
    private GroupsService groupsService;

    @GetMapping("/create/{adminId}")
    public CreateGroupResponse createGroup(@PathVariable("adminId") Long adminId) {
        return groupsService.createGroup(adminId);
    }

    @GetMapping("/addUser/{groupId}/{userId}")
    public void addUserToGroup(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        groupsService.addUserToGroup(groupId, userId);
    }

    @GetMapping("/{groupId}")
    public List<UsersResponse> getAllUsersForGroup(@PathVariable("groupId") Long groupId) {
        return groupsService.getAllUsersForGroup(groupId);
    }

    @GetMapping
    public List<GroupsResponse> getAllGroups() {
        return groupsService.getAllGroups();
    }

    @GetMapping("/{groupid}")
    public GroupsResponse getGroupByid(Long groupId) {
        return groupsService.getGroupByid(groupId);
    }
}

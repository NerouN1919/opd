package com.project.opd.Controllers;

import com.project.opd.Requests.LoginRequest;
import com.project.opd.Requests.RegistrationRequest;
import com.project.opd.Response.InfoResponse;
import com.project.opd.Response.LoginResponse;
import com.project.opd.Services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasAuthority('ADMIN')")
public class UsersController {
    @Autowired
    private UsersService usersService;
    @PostMapping()
    public void registration(@RequestBody RegistrationRequest reg) {
        usersService.registration(reg);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return usersService.login(loginRequest);
    }
    @GetMapping("/{id}")
    public InfoResponse getInfo(@PathVariable("id") Long id){
        return usersService.getInfo(id);
    }
}

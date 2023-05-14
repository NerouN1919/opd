package com.project.opd.Controllers;

import com.project.opd.Requests.*;
import com.project.opd.Services.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/projects")
@PreAuthorize("hasAuthority('ADMIN')")
public class ProjectsController {
    @Autowired
    private ProjectsService projectsService;

    @PostMapping()
    public void createProject(@RequestBody CreateProjectRequest createProjectRequest) {
        projectsService.createProject(createProjectRequest);
    }

    @PutMapping("addGroup")
    public void addGroup(@RequestBody AddGroupToProjectRequest addGroupToProjectRequest) {
        projectsService.addGroup(addGroupToProjectRequest);
    }
    @GetMapping("download_logo/{path}")
    public ResponseEntity<Object> downloadLogo(@PathVariable("path") String fileCode) {
        return projectsService.downloadLogo(fileCode);
    }
    @PostMapping("upload_logo")
    public UploadRequest uploadLogo(@RequestParam("file") MultipartFile multipartFile) throws IOException
    {
        return projectsService.uploadLogo(multipartFile);
    }
    @PutMapping("upload_logo_to_project")
    public void uploadLogoToProject(@RequestBody UploadLogoToPostRequest logoToPostRequest){
        projectsService.uploadLogoToProject(logoToPostRequest);
    }
    @PostMapping("upload_list")
    public UploadRequest uploadList(@RequestParam("file") MultipartFile multipartFile) throws IOException{
        return projectsService.uploadList(multipartFile);
    }
    @PostMapping("upload_table")
    public UploadRequest uploadTable(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return projectsService.uploadTable(multipartFile);
    }
    @GetMapping("download_list/{path}")
    public ResponseEntity<?> downloadList(@PathVariable("path") String fileCode) {
        return projectsService.downloadList(fileCode);
    }
    @GetMapping("download_table/{path}")
    public ResponseEntity<?> downloadTable(@PathVariable("path") String fileCode){
        return projectsService.downloadTable(fileCode);
    }
    @PutMapping("upload_table_to_project")
    public void uploadTableToProject(UploadTableToPostRequest tableToPostRequest){
        projectsService.uploadTableToProject(tableToPostRequest);
    }
    @PutMapping("upload_list_to_project")
    public void uploadListToProject(UploadListToPostRequest listToPostRequest){
        projectsService.uploadListToProject(listToPostRequest);
    }
}

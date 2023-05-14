package com.project.opd.Services;

import com.project.opd.Database.Groups;
import com.project.opd.Database.Projects;
import com.project.opd.Database.Users;
import com.project.opd.Errors.Failed;
import com.project.opd.FileWork.FileDownloadUtil;
import com.project.opd.FileWork.FileUploadUtil;
import com.project.opd.Repository.GroupsCrudRepository;
import com.project.opd.Repository.ProjectsCrudRepository;
import com.project.opd.Repository.UsersCrudRepository;
import com.project.opd.Requests.*;
import com.project.opd.Response.UploadResponse;
import com.project.opd.Secuirty.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ProjectsService {
    @Autowired
    private ProjectsCrudRepository projectsCrudRepository;
    @Autowired
    private UsersCrudRepository usersCrudRepository;
    @Autowired
    private GroupsCrudRepository groupsCrudRepository;
    public void createProject(CreateProjectRequest createProjectRequest){
        Optional<Users> usersOptional = usersCrudRepository.findById(createProjectRequest.getCreatorId());
        if(usersOptional.isEmpty()){
            throw new Failed("Bad creator id");
        }
        Users users = usersOptional.get();
        Projects projects = Projects.builder().createDate(new Date())
                .name(createProjectRequest.getName()).description(createProjectRequest.getDescription()).build();
        projects.setCreator(users);
        projects.setChangeDate(new Date());
        projectsCrudRepository.save(projects);
    }
    public void addGroup(AddGroupToProjectRequest addGroupToProjectRequest){
        Optional<Groups> optionalGroups = groupsCrudRepository.findById(addGroupToProjectRequest.getGroupId());
        if(optionalGroups.isEmpty()){
            throw new Failed("Bad group id");
        }
        Optional<Projects> optionalProjects = projectsCrudRepository.findById(addGroupToProjectRequest.getProjectId());
        if (optionalProjects.isEmpty()){
            throw new Failed("Bad project id");
        }
        Groups group = optionalGroups.get();
        Projects project = optionalProjects.get();
        project.setChangeDate(new Date());
        project.addGroup(group);
    }
    public ResponseEntity<Object> downloadLogo(String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource;
        try {
            resource = downloadUtil.getFileAsResource(fileCode, "Images");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null) {
            throw new Failed("File not found");
        }
        try {
            return new ResponseEntity<>(new UploadResponse(Base64.getEncoder().encodeToString(Files.readAllBytes(resource.getFile().toPath()))), HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public UploadRequest uploadLogo(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile, "Images");
        return new UploadRequest(filecode);
    }
    public void uploadLogoToProject(UploadLogoToPostRequest logoToPostRequest){
        Optional<Projects> optionalProjects = projectsCrudRepository.findById(logoToPostRequest.getProjectId());
        if(optionalProjects.isEmpty()){
            throw new Failed("Hasn't such project");
        }
        Projects project = optionalProjects.get();
        project.setChangeDate(new Date());
        project.setPathToLogo(logoToPostRequest.getPathToPhoto());
    }
    public UploadRequest uploadList(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile, "Lists");
        return new UploadRequest(filecode);
    }
    public void uploadListToProject(UploadListToPostRequest listToPostRequest){
        Optional<Projects> optionalProjects = projectsCrudRepository.findById(listToPostRequest.getProjectId());
        if(optionalProjects.isEmpty()){
            throw new Failed("Hasn't such project");
        }
        Projects project = optionalProjects.get();
        project.setChangeDate(new Date());
        project.setPathToList(listToPostRequest.getPathToList());
    }
    public UploadRequest uploadTable(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String filecode = FileUploadUtil.saveFile(fileName, multipartFile, "Tables");
        return new UploadRequest(filecode);
    }
    public void uploadTableToProject(UploadTableToPostRequest tableToPostRequest){
        Optional<Projects> optionalProjects = projectsCrudRepository.findById(tableToPostRequest.getProjectId());
        if(optionalProjects.isEmpty()){
            throw new Failed("Hasn't such project");
        }
        Projects project = optionalProjects.get();
        project.setChangeDate(new Date());
        project.setPathToList(tableToPostRequest.getPathToTable());
    }
    public ResponseEntity<?> downloadList(String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode, "Lists");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
    public ResponseEntity<?> downloadTable(String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();
        Resource resource = null;
        try {
            resource = downloadUtil.getFileAsResource(fileCode, "Tables");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}

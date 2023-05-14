package com.project.opd.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadTableToPostRequest {
    private Long projectId;
    private String pathToTable;
}

package com.project.opd.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadListToPostRequest {
    private Long projectId;
    private String pathToList;
}

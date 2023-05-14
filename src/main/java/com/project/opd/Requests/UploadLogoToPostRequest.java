package com.project.opd.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadLogoToPostRequest {
    private Long projectId;
    private String pathToPhoto;
}

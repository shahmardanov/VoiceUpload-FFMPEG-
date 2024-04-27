package com.example.fileupload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileResponse {

    Long id;
    String name;
    String url;
    Long size;
}

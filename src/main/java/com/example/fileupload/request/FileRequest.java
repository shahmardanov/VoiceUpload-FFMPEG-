package com.example.fileupload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileRequest {
    Long id;
    String name;
    String url;
    Long size;
}

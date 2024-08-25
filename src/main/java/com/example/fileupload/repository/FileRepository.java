package com.example.fileupload.repository;

import com.example.fileupload.model.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileData, Long> {

    Optional<FileData> findByFileName(String name);


}

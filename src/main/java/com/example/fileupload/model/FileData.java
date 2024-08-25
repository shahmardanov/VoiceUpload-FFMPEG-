package com.example.fileupload.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "data")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fileName;


    @Column(name = "user_id")
    private String userId;




}

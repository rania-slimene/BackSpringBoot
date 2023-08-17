package com.teamdevAcademy.academy.services;

import com.teamdevAcademy.academy.entities.File;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesService {
    void saveFile(File file);
}

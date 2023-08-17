package com.teamdevAcademy.academy.serviceImlp;

import com.teamdevAcademy.academy.repositories.FileRepository;
import com.teamdevAcademy.academy.services.FilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.teamdevAcademy.academy.entities.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Service
public class FilesServiceImpl implements FilesService {

    private final FileRepository fileRepository;

    @Autowired
    public FilesServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }


    @Override
    public void saveFile(File file) {
        fileRepository.save(file);
    }
}



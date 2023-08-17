package com.teamdevAcademy.academy.repositories;

import com.teamdevAcademy.academy.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository <File, Long>
{

}

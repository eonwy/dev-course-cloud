package com.grepp.spring.infra.util.file;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManager extends AbstractFileManager{

    @Value("${upload.path}")
    private String filePath;

    protected void uploadFile(MultipartFile file, FileDto fileDto) throws IOException {
        File path = new File(filePath + fileDto.savePath());
        if (!path.exists()) {
            path.mkdirs();
        }
        
        File target = new File(filePath + fileDto.savePath() + fileDto.renameFileName());
        file.transferTo(target);
    }
}

package com.project.airsoft.controller;

import com.project.airsoft.dto.SignRequest;
import com.project.airsoft.dto.SignResponse;
import com.project.airsoft.service.FlightService;
import com.project.airsoft.service.SignService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
@RequiredArgsConstructor
public class UploadController {

    private final FlightService flightService;


    @GetMapping("/adminupload")
    public String showForm() {
        return "uploadForm";
    }

    @PostMapping("/adminupload")
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        try {
            String uploadDir = "uploads/";
            Path uploadPath = Path.of(uploadDir);
            Files.createDirectories(uploadPath);

            String fileName = file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            processCsv(file);

            return "파일 업로드 완료";
        } catch (IOException e) {
            e.printStackTrace();
            return "파일 업로드 에러";
        }
    }

    private void processCsv(MultipartFile file) {
        String dateString1 = "2023.11.28";
        String dateString2 = "2023.12.15";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        LocalDate localDateTime1 = LocalDate.parse(dateString1, formatter);
        LocalDate localDateTime2 = LocalDate.parse(dateString2, formatter);
        flightService.processCsv(file, localDateTime1, localDateTime2);

    }
}


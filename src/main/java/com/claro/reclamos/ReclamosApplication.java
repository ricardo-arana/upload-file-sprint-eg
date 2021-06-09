package com.claro.reclamos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



@SpringBootApplication
@RestController
public class ReclamosApplication {

	List<String> files = new ArrayList<String>();
    private final Path rootLocation = Paths.get("/Users/raranare/Downloads/reclamos/upload");

	public static void main(String[] args) {
		SpringApplication.run(ReclamosApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/hola")
	public String hola(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hola %s!", name);
	}

	@PostMapping("/savefile")
   	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
      String message;
      try {
         try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
         } catch (Exception e) {
            throw new RuntimeException("FAIL!");
         }
         files.add(file.getOriginalFilename());

         message = "Successfully uploaded!";
         return ResponseEntity.status(HttpStatus.OK).body(message);
      } catch (Exception e) {
         message = "Failed to upload!";
         return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
      }
	}

}

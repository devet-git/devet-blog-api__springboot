package com.personal.devetblogapi.controller;

import com.personal.devetblogapi.annotation.SwaggerFormat;
import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.model.EntityResponseDto;
import com.personal.devetblogapi.service.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(AppEndpoint.ROOT_V1)
@Tag(name = "Files")
@CrossOrigin(
    value = {CrossOriginUrls.WEB_DEV, CrossOriginUrls.WEB_PROD},
    maxAge = 3600)
// @PreAuthorize("hasRole('ADMIN')")

public class FileController {
  @Autowired private FileService fileService;

  @SwaggerFormat(summary = "get all file")
  @GetMapping(AppEndpoint.File.getAll)
  public ResponseEntity<?> getAllFile() {
    return EntityResponseDto.success(
        MessageConst.success("Get all file"), fileService.getAllFile());
  }

  @SwaggerFormat(summary = "Upload files")
  @PostMapping(
      value = {AppEndpoint.File.upload},
      consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadMultiFile(
      @RequestPart("files") @RequestParam("files") List<MultipartFile> files) throws IOException {
    var imageUrls = fileService.uploadMultiFiles(files);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Upload file"), imageUrls);
  }
}

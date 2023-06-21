package com.personal.devetblogapi.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.entity.FileEntity;
import com.personal.devetblogapi.entity.UserEntity;
import com.personal.devetblogapi.exception.CustomException;
import com.personal.devetblogapi.model.FileDto;
import com.personal.devetblogapi.repo.FileRepo;
import com.personal.devetblogapi.repo.UserRepo;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
  @Autowired private Cloudinary cloudinary;
  @Autowired private UserRepo userRepo;
  @Autowired private FileRepo fileRepo;

  public List<?> getAllFile() {
    return fileRepo.findAll();
  }

  public FileEntity getFileById(String fileId) {
    return fileRepo
        .findById(fileId)
        .orElseThrow(
            () -> new CustomException(MessageConst.notExist("File"), HttpStatus.BAD_REQUEST, null));
  }

  public FileEntity getFileByUrl(String imgUrl) {
    return fileRepo
        .findByUrl(imgUrl)
        .orElseThrow(
            () -> new CustomException(MessageConst.notExist("File"), HttpStatus.BAD_REQUEST, null));
  }

  public ArrayList<FileDto.Response> uploadMultiFiles(List<MultipartFile> files)
      throws IOException {

    if (files.size() == 0) {
      throw new CustomException(MessageConst.notExist("User"), HttpStatus.BAD_REQUEST, null);
    }

    ArrayList<FileDto.Response> uploadedFileUrls = new ArrayList<>();
    ArrayList<FileEntity> storedFilesToDb = new ArrayList<>();

    // TODO: get logging user to system
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    UserEntity loggingUser = userRepo.findByEmail(auth.getName()).get();

    var uploadOptions = ObjectUtils.emptyMap();
    for (MultipartFile file : files) {
      File uploadedFile = convertMultiPartToFile(file);
      // TODO: upload to cloudinary
      var uploadResult = cloudinary.uploader().upload(uploadedFile, uploadOptions);

      // TODO: get file url have been uploaded
      String fileUrl = uploadResult.get("url").toString();
      String filePublicId = uploadResult.get("public_id").toString();

      FileEntity newFile =
          FileEntity.builder()
              .id(filePublicId)
              .posterId(loggingUser.getId())
              .url(fileUrl)
              .uploadedDate(new Date())
              .build();

      storedFilesToDb.add(newFile);
      uploadedFileUrls.add(new FileDto.Response(filePublicId, fileUrl));
      uploadedFile.delete();
    }
    fileRepo.saveAll(storedFilesToDb); // TODO: save to DB

    return uploadedFileUrls;
  }

  public void deleteFileByUrl(String imgUrl) throws IOException {
    FileEntity fileToDel = getFileByUrl(imgUrl);
    fileRepo.deleteById(fileToDel.getId());
    cloudinary.uploader().destroy(fileToDel.getId(), ObjectUtils.emptyMap());
  }

  public void deleteFileById(String id) throws IOException {
    fileRepo.deleteById(id);
    cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
  }

  private File convertMultiPartToFile(MultipartFile file) throws IOException {
    File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    FileOutputStream fos = new FileOutputStream(convFile);
    fos.write(file.getBytes());
    fos.close();
    return convFile;
  }
}

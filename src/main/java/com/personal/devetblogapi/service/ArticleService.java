package com.personal.devetblogapi.service;

import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.entity.ArticleEntity;
import com.personal.devetblogapi.entity.UserEntity;
import com.personal.devetblogapi.exception.CustomException;
import com.personal.devetblogapi.model.ArticleDto;
import com.personal.devetblogapi.model.UserDto;
import com.personal.devetblogapi.repo.ArticleRepo;
import com.personal.devetblogapi.util.EntityUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
  @Autowired private ArticleRepo articleRepo;
  @Autowired private AuthService authService;
  @Autowired private ModelMapper modelMapper;

  @Cacheable(value = "articles")
  public Object getAll(int pageNumber, int pageSize, String sortBy) {
    if (pageNumber <= 0)
      throw new CustomException(
          "pageNumber value must be 1 and more", HttpStatus.BAD_REQUEST, null);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
    Page<ArticleEntity> pageResult = articleRepo.findAll(pageable);

    Map<String, Object> result = new HashMap<>();
    result.put("totalArticle", pageResult.getTotalElements());
    result.put("articles", pageResult.getContent());

    return result;
  }

  @Cacheable(value = "articles", key = "articleByUserId")
  public Object getAllByUserId(String userId, int pageNumber, int pageSize, String sortBy) {
    if (pageNumber <= 0)
      throw new CustomException(
          "pageNumber value must be 1 and more", HttpStatus.BAD_REQUEST, null);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
    Page<ArticleEntity> pageResult = articleRepo.findAllByUserId(userId, pageable);

    Map<String, Object> result = new HashMap<>();
    result.put("totalArticle", pageResult.getTotalElements());
    result.put("articles", pageResult.getContent());

    return result;
  }

  @Cacheable(value = "articles", key = "articleForLoggingUser")
  public Object getAllForLoggingUser(int pageNumber, int pageSize, String sortBy) {
    if (pageNumber <= 0)
      throw new CustomException(
          "pageNumber value must be 1 and more", HttpStatus.BAD_REQUEST, null);
    Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.by(sortBy).ascending());
    UserEntity loggingUser = authService.getCurrentUser();
    Page<ArticleEntity> pageResult = articleRepo.findAllByUserId(loggingUser.getId(), pageable);

    Map<String, Object> result = new HashMap<>();
    result.put("totalArticle", pageResult.getTotalElements());
    result.put("articles", pageResult.getContent());

    return result;
  }

  @Cacheable(value = "article", key = "#id")
  public ArticleEntity getById(String articleId) {
    return articleRepo
        .findById(articleId)
        .orElseThrow(
            () ->
                new CustomException(
                    MessageConst.notExist("Article"), HttpStatus.BAD_REQUEST, null));
  }

  public void createOne(ArticleDto.Request req) {
    UserEntity currentUser = authService.getCurrentUser();

    ArticleEntity article =
        ArticleEntity.builder()
            .title(req.getTitle())
            .authors(req.getAuthors())
            .content(req.getContent())
            .description(req.getDescription())
            .createdDate(new Date())
            .images(req.getImages())
            .userId(currentUser.getId())
            .poster(modelMapper.map(currentUser, UserDto.Response.class))
            .build();
    articleRepo.save(article);
  }

  @CachePut(value = "article", key = "#articleId")
  public void updateById(String articleId, ArticleDto.Request req) {
    ArticleEntity updatedPost = articleRepo.findById(articleId).orElse(null);
    if (updatedPost == null) {
      createOne(req);
      return;
    }
    updatedPost.setUpdatedDate(new Date());
    updatedPost.setTitle(req.getTitle());
    updatedPost.setAuthors(req.getAuthors());
    updatedPost.setContent(req.getContent());
    updatedPost.setDescription(req.getDescription());

    articleRepo.save(updatedPost);
  }

  public <T> void updateById(String articleId, Map<String, T> fields) {
    ArticleEntity updatedPost = getById(articleId);
    EntityUtil.partialUpdate(updatedPost, fields);
    articleRepo.save(updatedPost);
  }

  @CacheEvict(value = "product", key = "#articleId")
  public void deleteOneById(String articleId) {
    articleRepo.deleteById(articleId);
  }

  public List<ArticleEntity> searchWithTitle(String keyword) {
    return articleRepo.findByTitleIgnoreCaseContaining(keyword);
  }
}

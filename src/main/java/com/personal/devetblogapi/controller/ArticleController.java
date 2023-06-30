package com.personal.devetblogapi.controller;

import com.personal.devetblogapi.annotation.SwaggerFormat;
import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import com.personal.devetblogapi.constant.MessageConst;
import com.personal.devetblogapi.entity.ArticleEntity;
import com.personal.devetblogapi.model.ArticleDto;
import com.personal.devetblogapi.model.EntityResponseDto;
import com.personal.devetblogapi.service.ArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppEndpoint.ROOT_V1)
@Tag(name = "Articles")
@CrossOrigin(
    value = {CrossOriginUrls.WEB_DEV, CrossOriginUrls.WEB_PROD},
    maxAge = 3600)
// @PreAuthorize("hasRole('ADMIN')")

public class ArticleController {
  @Autowired private ArticleService articleService;

  @PermitAll
  @SwaggerFormat(
      summary = "List all articles",
      security = {})
  @GetMapping(AppEndpoint.Article.LIST_ALL)
  public ResponseEntity<?> listAllArticles(
      @RequestParam(defaultValue = "1") int pageNumber,
      @RequestParam(defaultValue = "1") int pageSize,
      @RequestParam(defaultValue = "title") String sortBy) {

    var response = articleService.getAll(pageNumber, pageSize, sortBy);

    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("List all articles"), response);
  }

  @SwaggerFormat(
      summary = "List all articles by user id",
      security = {})
  @GetMapping(AppEndpoint.Article.LIST_ALL_BY_USER_ID)
  public ResponseEntity<?> listAllArticlesByUserId(
      @PathVariable("id") String userId,
      @RequestParam(defaultValue = "1") int pageNumber,
      @RequestParam(defaultValue = "1") int pageSize,
      @RequestParam(defaultValue = "title") String sortBy) {
    var res = articleService.getAllByUserId(userId, pageNumber, pageSize, sortBy);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("List articles"), res);
  }

  @SwaggerFormat(summary = "Get articles by id")
  @GetMapping(AppEndpoint.Article.GET_BY_ID)
  public ResponseEntity<?> getById(@PathVariable("id") String articleId) {
    ArticleEntity articles = articleService.getById(articleId);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Get article with id: " + articleId), articles);
  }

  @SwaggerFormat(summary = "Create an article")
  @PostMapping(AppEndpoint.Article.CREATE)
  public ResponseEntity<?> createOne(@RequestBody ArticleDto.Request req) {
    articleService.createOne(req);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Create new article"), null);
  }

  @SwaggerFormat(summary = "Update an article")
  @PutMapping(AppEndpoint.Article.UPDATE)
  public ResponseEntity<?> updateById(
      @PathVariable("id") String articleId, @RequestBody ArticleDto.Request req) {
    articleService.updateById(articleId, req);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Update article"), null);
  }

  @SwaggerFormat(summary = "Update a part of the article by id")
  @PatchMapping(AppEndpoint.Article.UPDATE)
  public <T> ResponseEntity<?> updateById(
      @PathVariable("id") String articleId, @RequestBody Map<String, T> req) {
    articleService.updateById(articleId, req);
    return EntityResponseDto.success(HttpStatus.OK, MessageConst.success("Update article"), null);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @SwaggerFormat(summary = "Delete an article(only ADMIN)")
  @DeleteMapping(AppEndpoint.Article.DELETE)
  public ResponseEntity<?> deleteOneById(@PathVariable("id") String articleId) {
    articleService.deleteOneById(articleId);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Delete article with id: " + articleId), null);
  }

  @SwaggerFormat(
      summary = "Search articles",
      security = {})
  @GetMapping(AppEndpoint.Article.SEARCH)
  public ResponseEntity<?> searchWithTitle(
      @RequestParam(value = "by", required = false) String searchBy,
      @RequestParam("keyword") String keyword) {
    var res = articleService.searchWithTitle(keyword);
    return EntityResponseDto.success(
        HttpStatus.OK, MessageConst.success("Search article with keyword: " + keyword), res);
  }
}

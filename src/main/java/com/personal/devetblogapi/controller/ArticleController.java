package com.personal.devetblogapi.controller;

import com.personal.devetblogapi.constant.AppEndpoint;
import com.personal.devetblogapi.constant.CrossOriginUrls;
import com.personal.devetblogapi.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppEndpoint.Article.BASE)
@Tag(name = "Article APIs")
@CrossOrigin(
    value = {CrossOriginUrls.WEB_DEV, CrossOriginUrls.WEB_PROD},
    maxAge = 3600)
public class ArticleController {
  @Autowired private ArticleService articleService;

  @Operation(
      summary = "Register",
      //      security = {@SecurityRequirement(name = "bearer-key")},
      //      tags = {"haha", "hihi"},
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Successful"
            //            content = {
            //              @Content(
            //                  mediaType = "application/json",
            //                  schema = @Schema(implementation = DataResponseDto.class))
            //            }
            ),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request"
            //            content = {
            //              @Content(
            //                  mediaType = "application/json",
            //                  schema = @Schema(implementation = DataResponseDto.class))
            //            }
            ),
      })
  @GetMapping(AppEndpoint.Article.get)
  public ResponseEntity<?> get() {
    articleService.createArticle();
    return ResponseEntity.ok().body("HELLO");
  }
}

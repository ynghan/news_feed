package com.sparta.springprepare.aws;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.springprepare.dto.PhotoDto;
import com.sparta.springprepare.security.UserDetailsImpl;
import com.sparta.springprepare.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final AmazonS3Client amazonS3Client;
    private final UserService userService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @PostMapping("/aws/upload")
    public ResponseEntity<PhotoDto> uploadFile(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            String folderName = URLEncoder.encode("이미지 폴더", StandardCharsets.UTF_8);
            String fileName = file.getOriginalFilename();
            String fileUrl = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + folderName + "/" + fileName;
            ObjectMetadata metaData = new ObjectMetadata();
            metaData.setContentType(file.getContentType());
            metaData.setContentLength(file.getSize());
            amazonS3Client.putObject(new PutObjectRequest(bucket, "이미지 폴더/" + fileName, file.getInputStream(), metaData)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            // 사용자 이미지 url 저장
            PhotoDto photoDto = userService.postPhoto(fileUrl, userDetails.getUser());

            return ResponseEntity.ok(photoDto);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

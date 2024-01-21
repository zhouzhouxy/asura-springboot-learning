package com.asura.minio.service;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @author zzyx 2024/1/14
 */
@Service
public class FileStorageService {
    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String url;

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public void uploadFile(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(file.getOriginalFilename()).stream(
                                    is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }




    public String uploadFileReturnUrl(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {
            // 生成基于日期的文件路径
            String folderPath = dateFormat.format(new Date());
            String objectName = folderPath + "/" + generateRandomString()+"."+getFileExtension(file.getOriginalFilename());
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName)
                            .object(objectName).stream(is, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            return url+bucketName+"/"+objectName;
        } catch (Exception e) {
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public static String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        // 处理复合扩展名的特例，例如 .tar.gz
        if (fileName.endsWith(".tar.gz")) {
            return "tar.gz";
        }
        // 添加更多特例处理逻辑（如果需要）

        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex >= 0) {
            // 获取最后一个点之后的所有字符作为后缀名
            return fileName.substring(dotIndex + 1);
        } else {
            // 没有找到点或点是文件名的最后一个字符
            return "";
        }
    }

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 20;
    private static final Random random = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


}

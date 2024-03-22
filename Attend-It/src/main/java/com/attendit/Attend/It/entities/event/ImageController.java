package com.attendit.Attend.It.entities.event;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

    private static final String IMAGE_FOLDER = "C:\\Users\\YoussefAoun\\OneDrive - Bitify PTY LTD\\Desktop\\Uni stuff\\ISD\\Attend'It\\Backend\\Attend-It\\events_images\\";
    private static final MediaType MEDIA_TYPE_WEBP = new MediaType("image", "webp");


    @GetMapping("/images/{imageTitle}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageTitle) {
        try {
            Path imagePath = Paths.get(IMAGE_FOLDER + imageTitle);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                MediaType contentType = determineContentType(imageTitle);
                return ResponseEntity.ok()
                        .contentType(contentType) // Adjust content type according to your image type
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
    private MediaType determineContentType(String fileName) {
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        switch (fileExtension) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "webp":
                return MEDIA_TYPE_WEBP;
            default:
                // Default to image/jpeg for unknown file types
                return MediaType.IMAGE_JPEG;
        }
    }
}

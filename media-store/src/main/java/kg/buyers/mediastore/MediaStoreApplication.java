package kg.buyers.mediastore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootApplication
@RestController
@RequestMapping("api/v1/media/")
public class MediaStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediaStoreApplication.class, args);
    }
    @CrossOrigin("*")
    @PostMapping("/upload/{productId}")
    public ResponseEntity<Map<String, String>> uploadPhoto(@RequestParam("file") MultipartFile file, @PathVariable String productId) {
        try {
            // Create directory if not exists
            File directory = new File("media/"+productId);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Generate unique filename
            String fileName = UUID.randomUUID().toString() + ".jpg";

            // Save file to disk
            File saveFile = new File(directory, fileName);
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            Map<String, String> res = new HashMap<>();
            res.put("fileUrl", "http://localhost:8081/api/v1/media/photo/" + productId + "/" + fileName);
            return ResponseEntity.ok(res);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<String, String>());
        }
    }
    @CrossOrigin("*")
    @GetMapping("/photo/{productId}/{photoId}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable String productId, @PathVariable String photoId) {
        try {
            File file = new File("media/"+productId+"/"+photoId);
            if (file.exists()) {
                byte[] data = org.apache.commons.io.FileUtils.readFileToByteArray(file);
                return ResponseEntity.ok(data);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @CrossOrigin("*")
    @DeleteMapping("/delete/{productId}/{photoId}")
    public ResponseEntity<String> deletePhoto(@PathVariable String productId, @PathVariable String photoId) {
        try {
            File file = new File("media/"+productId+"/"+photoId);
            if (file.exists()) {
                file.delete();
                return ResponseEntity.ok("Photo deleted successfully. ID: " + photoId);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete photo.");
        }
    }
}

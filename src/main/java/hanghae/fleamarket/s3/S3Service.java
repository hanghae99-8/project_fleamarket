package hanghae.fleamarket.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class S3Service {

    private final S3Repository s3Repository;

    @Autowired
    private S3Uploader s3Uploader;

    public String saveImage(MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String savedName = s3Uploader.upload(image, "images");

            String uploadName = image.getOriginalFilename();
            S3Entity entity = new S3Entity(savedName, uploadName);
            s3Repository.save(entity);
            return "성공";
        }
        return "실패";
    }
}

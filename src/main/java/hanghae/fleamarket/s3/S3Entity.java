package hanghae.fleamarket.s3;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class S3Entity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgUrl;
    private String uploadName;

    public S3Entity(String imgUrl, String uploadName) {
        this.imgUrl = imgUrl;
        this.uploadName = uploadName;
    }

}

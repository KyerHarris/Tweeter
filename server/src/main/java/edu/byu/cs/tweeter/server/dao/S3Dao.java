package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Date;

public class S3Dao implements S3DaoInterface {
    private final AmazonS3 s3;
    private static final String BUCKET_NAME = "twitter-kyerharris";
    private static final int URL_EXPIRATION = 60 * 60 * 1000;

    public S3Dao() {
        this.s3 = AmazonS3ClientBuilder.standard()
                .withRegion("us-east-2")
                .build();
    }

    @Override
    public String uploadImage(String alias, String imageString) {
        byte[] byteArray = Base64.getDecoder().decode(imageString);
        ObjectMetadata data = new ObjectMetadata();
        data.setContentLength(byteArray.length);
        data.setContentType("image/jpeg");

        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, alias, new ByteArrayInputStream(byteArray), data)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(request);
        return s3.getUrl(BUCKET_NAME, alias).toString();

    }
}

package edu.byu.cs.tweeter.server.dao;
import java.net.URL;

public interface S3DaoInterface {

    /**
     * Uploads an image to the S3 bucket and returns the link to the uploaded image.
     *
     * @param alias       Unique identifier for the image, typically the user's alias.
     * @param imageString Base64 encoded string of the image.
     * @return The link to the uploaded image.
     */
    String uploadImage(String alias, String imageString);

}

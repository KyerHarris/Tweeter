package edu.byu.cs.tweeter.server.service;

import java.time.Instant;
import java.util.UUID;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.request.RegisterRequest;
import edu.byu.cs.tweeter.model.net.response.RegisterResponse;
import edu.byu.cs.tweeter.server.abstractFactory.DaoFactory;
import edu.byu.cs.tweeter.server.dao.S3DaoInterface;
import edu.byu.cs.tweeter.server.dao.UserDaoInterface;

public class RegisterService extends AuthenticateService{
    S3DaoInterface s3Dao;
    public RegisterService(DaoFactory daoFactory) {
        super(daoFactory);
        s3Dao = daoFactory.createS3Dao();
    }
    public RegisterResponse register(RegisterRequest request) {
        if(request.getUsername() == null){
            throw new RuntimeException("[Bad Request] Missing a username");
        } else if(request.getPassword() == null) {
            throw new RuntimeException("[Bad Request] Missing a password");
        } else if(request.getFirstName() == null) {
            throw new RuntimeException("[Bad Request] Missing a first name");
        } else if(request.getLastName() == null) {
            throw new RuntimeException("[Bad Request] Missing a last name");
        } else if(request.getImage() == null) {
            throw new RuntimeException("[Bad Request] Missing an image");
        }

        String imageURL = s3Dao.uploadImage(request.getFirstName(), request.getImage());
        userDao.create(request.getFirstName(), request.getLastName(), request.getUsername(), imageURL, request.getPassword());
        return new RegisterResponse(new User(request.getFirstName(), request.getLastName(), request.getUsername(), request.getImage()), generateAuthToken(request.getUsername()));
    }

}

package edu.byu.cs.tweeter.client.network;


import edu.byu.cs.tweeter.model.net.response.*;
import edu.byu.cs.tweeter.model.net.request.*;
import okhttp3.*;
import okhttp3.Response;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerFacade {
    private static final OkHttpClient httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();
    private static final String BASE_URL = "https://2ahwxd014g.execute-api.us-east-2.amazonaws.com/Development";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final Gson gson = new Gson();
    public static FollowResponse follow(FollowRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/follow").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), FollowResponse.class);
            } else {
                return new FollowResponse(false, "Following failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new FollowResponse(false, "IOException during follow request: " + e.getMessage());
        }
    }
    public static UnfollowResponse unfollow(UnfollowRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/unfollow").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), UnfollowResponse.class);
            } else {
                return new UnfollowResponse(false, "Unfollowing failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new UnfollowResponse(false, "IOException during unfollow request: " + e.getMessage());
        }
    }
    public static FeedResponse getFeed(FeedRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/feed").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), FeedResponse.class);
            } else {
                return new FeedResponse("Fetching feed failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new FeedResponse("IOException during feed request: " + e.getMessage());
        }
    }
    public static StoryResponse getStory(StoryRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/story").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String resp = response.body().string();
                return gson.fromJson(resp, StoryResponse.class);
            } else {
                // Handle non-successful response
                return new StoryResponse("Fetching story failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new StoryResponse("IOException during story request: " + e.getMessage());
        }
    }
    public static FollowersCountResponse getFollowersCount(FollowersCountRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/followerscount").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String input = response.body().string();
                return gson.fromJson(input, FollowersCountResponse.class);
            } else {
                String input = response.body().string();
                return new FollowersCountResponse(false, "Fetching followers count failed with status code: " + response.code(), 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new FollowersCountResponse(false, "IOException during followers count request: " + e.getMessage(), 0);
        }
    }
    public static FollowingCountResponse getFollowingCount(FollowingCountRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/followingcount").newBuilder().build(); // URL for the following count endpoint
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String input = response.body().string();
                return gson.fromJson(input, FollowingCountResponse.class);
            } else {
                // Handle non-successful response
                return new FollowingCountResponse(false, "Fetching following count failed with status code: " + response.code(), 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new FollowingCountResponse(false, "IOException during following count request: " + e.getMessage(), 0);
        }
    }
    public static FollowersResponse getFollowers(FollowersRequest request){
        HttpUrl url = HttpUrl.parse(BASE_URL + "/followers").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String input = response.body().string();
                return gson.fromJson(input, FollowersResponse.class);
            } else {
                return new FollowersResponse("Fetching followers failed with status code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new FollowersResponse("IOException during followers request: " + e.getMessage());
        }
    }
    public static FollowingResponse getFollowing(FollowingRequest request){
        HttpUrl url = HttpUrl.parse(BASE_URL + "/following").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String input = response.body().string();
                return gson.fromJson(input, FollowingResponse.class);
            } else {
                String input = response.body().string();
                return new FollowingResponse("Fetching following failed with status code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new FollowingResponse("IOException during following request: " + e.getMessage());
        }
    }

    public static UserResponse getUser(UserRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/user").newBuilder().build(); // URL for the user endpoint
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body) // Assuming user info retrieval uses a POST request
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String resp = response.body().string();
                return gson.fromJson(resp, UserResponse.class);
            } else {
                // Handle non-successful response
                return new UserResponse(false, "Fetching user failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new UserResponse(false, "IOException during user request: " + e.getMessage());
        }
    }
    public static IsFollowerResponse isFollower(IsFollowerRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/isfollower").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), IsFollowerResponse.class);
            } else {
                return new IsFollowerResponse(false, "IsFollower check failed with status code: " + response.code(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new IsFollowerResponse(false, "IOException during IsFollower request: " + e.getMessage(), false);
        }
    }
    public static LoginResponse login(LoginRequest request){
        HttpUrl url = HttpUrl.parse(BASE_URL +"/login").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful()) {
                String res = response.body().string();
                return gson.fromJson(res, LoginResponse.class);
            } else {
                return new LoginResponse("Login failed with status code: " + response.code());
            }
        } catch (IOException e) {
            return new LoginResponse("IOException during login request: " + e.getMessage());
        }
    }
    public static RegisterResponse register(RegisterRequest request) {
        HttpUrl url = HttpUrl.parse(BASE_URL + "/register").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), RegisterResponse.class);
            } else {
                // Handle non-successful response
                return new RegisterResponse("Registration failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new RegisterResponse("IOException during registration request: " + e.getMessage());
        }
    }
    public static LogoutResponse logout(LogoutRequest request){
        HttpUrl url = HttpUrl.parse(BASE_URL + "/logout").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(httpRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().string(), LogoutResponse.class);
            } else {
                return new LogoutResponse(false, "Logout failed with status code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new LogoutResponse(false, "IOException during logout request: " + e.getMessage());
        }
    }
    public static PostStatusResponse postStatus(PostStatusRequest request){
        HttpUrl url = HttpUrl.parse(BASE_URL + "/poststatus").newBuilder().build();
        RequestBody body = RequestBody.create(JSON, gson.toJson(request));

        Request httpRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        httpClient.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Handle failure scenario, e.g., logging the error
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    System.err.println("Post status failed with status code: " + response.code());
                }
                response.close();
            }
        });

        return new PostStatusResponse(true, "Post successful");
    }
}

package com.example.whereiamdisplay;

/**
 * Created by Rishabh on 21/07/2017.
 */

public class PostPojo {

    private String postImage, postText, userId, postId,postUserText;

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
    public String getPostUserText() {
        return postUserText;
    }

    public void setPostUserText(String postUserText) {
        this.postUserText = postUserText;
    }
    @Override
    public String toString() {
        return "PostPojo{" +
                "postImage='" + postImage + '\'' +
                ", postText='" + postText + '\'' +
                ", userId='" + userId + '\'' +
                ", postId='" + postId + '\'' +
                ", posUserText='" + postUserText + '\'' +
                '}';
    }


}

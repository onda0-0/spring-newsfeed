package com.sparta.springnewsfeed;

import com.sparta.springnewsfeed.comment.Comment;
import com.sparta.springnewsfeed.comment.CommentRepository;
import com.sparta.springnewsfeed.like.CommentLike;
import com.sparta.springnewsfeed.like.CommentLikeId;
import com.sparta.springnewsfeed.like.CommentLikeRepository;
import com.sparta.springnewsfeed.like.PostLike;
import com.sparta.springnewsfeed.like.PostLikeId;
import com.sparta.springnewsfeed.like.PostLikeRepository;
import com.sparta.springnewsfeed.post.Post;
import com.sparta.springnewsfeed.post.PostRepository;
import com.sparta.springnewsfeed.user.User;
import com.sparta.springnewsfeed.user.UserRepository;
import com.sparta.springnewsfeed.user.UserStatusEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class SpringNewsfeedApplicationTests {


}
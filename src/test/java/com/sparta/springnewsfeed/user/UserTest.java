package com.sparta.springnewsfeed.user;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.sparta.springnewsfeed.comment.Comment;
import com.sparta.springnewsfeed.post.Post;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    private static Validator validator;
    private static FixtureMonkey fixtureMonkey;

    @BeforeAll
    public static void setUp() {
        // ValidatorFactory와 FixtureMonkey 설정
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }

    @Test
    @DisplayName("User 엔티티 기본 생성 테스트")
    public void testUserCreation() {
        // Given: FixtureMonkey를 사용하여 User 객체 생성
        User user = fixtureMonkey.giveMeOne(User.class);

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then: 모두 유효한지 확인
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("User ID 유효성 검사")
    public void testUserIdValidation() {
        // Given: userId를 빈 문자열로 설정
        User user = fixtureMonkey.giveMeBuilder(User.class)
                .set("userId", "")
                .sample();

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then: 유효성 검증에 실패하는지 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("사용자 ID는 필수 입력 값입니다."));
    }

    @Test
    @DisplayName("Password 유효성 검사")
    public void testPasswordValidation() {
        // Given: password를 빈 문자열로 설정
        User user = fixtureMonkey.giveMeBuilder(User.class)
                .set("password", "")
                .sample();

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then: 유효성 검증에 실패하는지 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("비밀번호는 필수 입력 값입니다."));
    }

    @Test
    @DisplayName("Email 유효성 검사")
    public void testEmailValidation() {
        // Given: email을 잘못된 형식으로 설정
        User user = fixtureMonkey.giveMeBuilder(User.class)
                .set("email", "invalid-email")
                .sample();

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then: 유효성 검증에 실패하는지 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("올바른 이메일 형식이어야 합니다."));
    }

    @Test
    @DisplayName("User 엔티티의 관계 테스트")
    public void testUserRelationships() {
        // Given: User 객체 생성
        User user = new User();
        user.setUserId("testUser");
        user.setPassword("ValidPassword123@");
        user.setEmail("valid@example.com");
        user.setStatus(UserStatusEnum.VERIFIED);

        Comment comment = new Comment();
        comment.setUser(user);
        user.getComments().add(comment);

        Post post = new Post();
        post.setUser(user);
        user.getPosts().add(post);

        // When: User 객체 검증
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then: 모두 유효한지 확인
        assertThat(violations).isEmpty();
        assertThat(user.getComments()).hasSize(1);
        assertThat(user.getPosts()).hasSize(1);
    }

}

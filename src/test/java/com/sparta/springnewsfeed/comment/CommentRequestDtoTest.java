package com.sparta.springnewsfeed.comment;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentRequestDtoTest {
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
    @DisplayName("CommentRequestDto 유효성 검사")
    public void testCommentRequestDtoValidation() {
        // Given: FixtureMonkey를 사용하여 CommentRequestDto 객체 생성
        CommentRequestDto dto = fixtureMonkey.giveMeOne(CommentRequestDto.class);

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<CommentRequestDto>> violations = validator.validate(dto);

        // Then: 모두 유효한지 확인
        assertThat(violations).isEmpty();
    }

}

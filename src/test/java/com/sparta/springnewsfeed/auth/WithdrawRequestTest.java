package com.sparta.springnewsfeed.auth;

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

public class WithdrawRequestTest {
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
    @DisplayName("WithdrawRequestDto 유효성 검사")
    public void testWithdrawRequestDtoValidation() {
        // Given: FixtureMonkey를 사용하여 WithdrawRequestDto 객체 생성
        WithdrawRequestDto dto = fixtureMonkey.giveMeOne(WithdrawRequestDto.class);

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<WithdrawRequestDto>> violations = validator.validate(dto);

        // Then: 모두 유효한지 확인
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("비밀번호 빈 문자열 유효성 검사")
    public void testBlankPassword() {
        // Given: password를 빈 문자열로 설정
        WithdrawRequestDto dto = fixtureMonkey.giveMeBuilder(WithdrawRequestDto.class)
                .set("password", "")
                .sample();

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<WithdrawRequestDto>> violations = validator.validate(dto);

        // Then: 유효성 검증에 실패하는지 확인
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("공백일 수 없습니다"));
    }
}

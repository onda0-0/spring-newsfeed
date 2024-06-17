package com.sparta.springnewsfeed.user.dto;

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

public class UpdatePasswordRequestDtoTest {
    private static Validator validator;
    private static FixtureMonkey fixtureMonkey;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        fixtureMonkey = FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .build();
    }


    /*public UpdatePasswordRequestDtoTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
*/
    @Test
    @DisplayName("UpdatePasswordRequestDto 생성 및 유효성 검증 테스트")
    public void testUpdatePasswordRequestDto() {
        // Given :UpdatePasswordRequestDto 데이터 생성
        UpdatePasswordRequestDto dto = fixtureMonkey.giveMeOne(UpdatePasswordRequestDto.class);

        // When: 생성된 데이터 검증
        Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(dto);

        // Then: 모두 유효한지
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("현재 비밀번호가 빈 문자열일 때 유효성 검증 테스트")
    public void testBlankCurrentPassword() {
        UpdatePasswordRequestDto dto = fixtureMonkey.giveMeBuilder(UpdatePasswordRequestDto.class)
                .set("currentPassword", "")
                .sample();

        Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("현재 비밀번호는 필수 입력 값입니다."));
    }

    @Test
    @DisplayName("새 비밀번호가 유효하지 않을 때 유효성 검증 테스트")
    public void testInvalidNewPassword() {
        UpdatePasswordRequestDto dto = fixtureMonkey.giveMeBuilder(UpdatePasswordRequestDto.class)
                .set("newPassword", "password")
                .sample();

        Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("새 비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다."));
    }

    @Test
    @DisplayName("새 비밀번호가 너무 짧을 때 유효성 검증 테스트")
    public void testShortNewPassword() {
        UpdatePasswordRequestDto dto = fixtureMonkey.giveMeBuilder(UpdatePasswordRequestDto.class)
                .set("newPassword", "short1!")
                .sample();

        Set<ConstraintViolation<UpdatePasswordRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(violation -> violation.getMessage().contains("새 비밀번호는 최소 10글자 이상이어야 합니다."));
    }
}


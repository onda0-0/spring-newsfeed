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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestDtoTest {

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


    @Test
    @DisplayName("사용자 ID 빈 문자열 입력시 실패 테스트")
    public void testUserIdNotBlank() {
        // Given
        SignupRequestDto dto = new SignupRequestDto("", "ValidPassword123@", "valid@example.com");

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());

        for (ConstraintViolation<SignupRequestDto> violation : violations) {
            String message = violation.getMessage();
            assertTrue(
                    message.equals("사용자 ID는 필수 입력 값입니다.") ||
                            message.equals("사용자 ID는 최소 10글자 이상, 최대 20글자 이하여야 합니다.") ||
                            message.equals("사용자 ID는 대소문자 포함 영문 + 숫자만을 허용합니다.")
            );
        }
    }

    @Test
    @DisplayName("비밀번호 빈 문자열 입력시 실패 테스트")
    public void testPasswordNotBlank() {
        // Given
        SignupRequestDto dto = new SignupRequestDto("ValidUserId", "", "valid@example.com");

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());

        for (ConstraintViolation<SignupRequestDto> violation : violations) {
            String message = violation.getMessage();
            assertTrue(
                    message.equals("비밀번호는 필수 입력 값입니다.") ||
                            message.equals("비밀번호는 최소 10글자 이상이어야 합니다.") ||
                            message.equals("비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 포함해야 합니다.")
            );
        }
    }

    @Test
    @DisplayName("이메일 빈 문자열 입력시 실패 테스트")
    public void testEmailNotBlank() {
        // Given
        SignupRequestDto dto = new SignupRequestDto("ValidUserId", "ValidPassword123@", "");

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        for (ConstraintViolation<SignupRequestDto> violation : violations) {
            String message = violation.getMessage();
            assertEquals("이메일은 필수 입력 값입니다.", message);
        }
    }

    @Test
    @DisplayName("이메일 형식이 잘못되었을 때 실패 테스트")
    public void testEmailInvalidFormat() {
        // Given
        SignupRequestDto dto = new SignupRequestDto("ValidUserId", "ValidPassword123@", "invalid-email");

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());

        for (ConstraintViolation<SignupRequestDto> violation : violations) {
            String message = violation.getMessage();
            assertEquals("올바른 이메일 형식이어야 합니다.", message);
        }
    }

    @Test
    @DisplayName("DTO 자동 생성 및 검증 테스트")
    public void testDtoCreationAndValidation() {
        // Given
        SignupRequestDto dto = fixtureMonkey.giveMeBuilder(SignupRequestDto.class)
                .set("userId", "ValidUser1234")
                .set("password", "ValidPassword123@")
                .set("email", "valid@example.com")
                .sample();

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("getter 메서드 테스트")
    public void testGetter() {
        // Given
        SignupRequestDto dto = fixtureMonkey.giveMeBuilder(SignupRequestDto.class)
                .set("userId", "ValidUser1234")
                .set("password", "ValidPassword123@")
                .set("email", "valid@example.com")
                .sample();

        // When & Then
        assertNotNull(dto.getUserId());
        assertNotNull(dto.getPassword());
        assertNotNull(dto.getEmail());
    }

    @Test
    @DisplayName("equals(), hashCode(), toString() 메서드 테스트")
    public void testEqualsAndHashCodeAndToString() {
        // Given
        SignupRequestDto dto1 = new SignupRequestDto("ValidUserId", "ValidPassword123@", "valid@example.com");
        SignupRequestDto dto2 = new SignupRequestDto("ValidUserId", "ValidPassword123@", "valid@example.com");
        SignupRequestDto dto3 = new SignupRequestDto("OtherUserId", "OtherPassword123@", "other@example.com");

        // equals() 및 hashCode() 테스트
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());

        // toString() 테스트
        String toString = dto1.toString();
        assertTrue(toString.contains("ValidUserId"));
        assertTrue(toString.contains("ValidPassword123@"));
        assertTrue(toString.contains("valid@example.com"));
    }
}

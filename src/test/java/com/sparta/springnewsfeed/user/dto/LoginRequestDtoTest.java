package com.sparta.springnewsfeed.user.dto;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginRequestDtoTest {

    FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    public void testLoginRequestDtoCreation() {
        // Given :LoginRequestDto 데이터 생성
        LoginRequestDto loginRequestDto = fixtureMonkey.giveMeOne(LoginRequestDto.class);

        // When: 생성된 데이터 검증

        // Then: 필드들이 null이 아닌지 확인
        assertNotNull(loginRequestDto.getUserId());
        assertNotNull(loginRequestDto.getPassword());
    }

    @Test
    public void testLoginRequestDtoCustomData() {
        // Fixture Monkey를 사용하여 특정 조건을 가진 LoginRequestDto 데이터 생성
        LoginRequestDto loginRequestDto = fixtureMonkey.giveMeBuilder(LoginRequestDto.class)
                .set("userId", "testUser")
                .set("password", "testPassword")
                .sample();

        // 생성된 데이터 검증
        assertEquals("testUser", loginRequestDto.getUserId());
        assertEquals("testPassword", loginRequestDto.getPassword());
    }
}
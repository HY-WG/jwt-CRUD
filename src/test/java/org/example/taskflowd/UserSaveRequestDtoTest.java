package org.example.taskflowd;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.taskflowd.domain.user.dto.request.UserSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserSaveRequestDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void 유효한_DTO_검증_성공() {
        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName("hy123")
                .email("test@test.com")
                .password("Test123!")
                .build();

        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty(); // 검증 오류 없어야 성공
    }

    @Test
    void 유저명이_짧으면_검증_실패() {
        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName("hy")
                .email("test@test.com")
                .password("Test123!")
                .build();

        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void 이메일형식이_틀리면_검증_실패() {
        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName("hy123")
                .email("invalid-email")
                .password("Test123!")
                .build();

        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }

    @Test
    void 비밀번호가_조건을_충족하지않으면_검증_실패() {
        UserSaveRequestDto dto = UserSaveRequestDto.builder()
                .userName("hy123")
                .email("test@test.com")
                .password("password") // 특수문자 없음
                .build();

        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
    }
}

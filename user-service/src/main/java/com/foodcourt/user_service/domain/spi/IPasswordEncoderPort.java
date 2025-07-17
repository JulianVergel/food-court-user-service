package com.foodcourt.user_service.domain.spi;

public interface IPasswordEncoderPort {
    String encodePassword(String password);
}

package com.example.userservice.service.imp;

import com.example.userservice.entity.UserEntity;
import com.example.userservice.model.requestbody.ValidateTokenRequest;
import com.example.userservice.model.responsebody.ValidateTokenResponse;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.TokenService;
import com.example.userservice.util.Common;
import com.example.userservice.util.ErrorCode;
import com.example.userservice.util.JwtTokenUtil;
import com.example.userservice.util.KMAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenServiceImp implements TokenService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImp.class);
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Autowired
    public TokenServiceImp(JwtTokenUtil jwtTokenUtil, UserRepository userRepository) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
    }

    @Override
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) throws KMAException {
        try {
            ValidateTokenResponse response = new ValidateTokenResponse();
            String userName = jwtTokenUtil.getUsernameFromToken(validateTokenRequest.getToken());
            UserEntity user = userRepository.findByUserName(userName);
            if (Objects.isNull(user)) {
                response.setResponseCode(ErrorCode.BAD_REQUEST);
                response.setResponseMessage(Common.FAIL);
                LOGGER.info("Token validate with response {}", response);
                return response;
            }
            response.setResponseCode(ErrorCode.SUCCESS);
            response.setResponseMessage(Common.SUCCESS);
            LOGGER.info("Token validate with response {}", response);
            return response;
        } catch (Exception e) {
            LOGGER.error("Token invalid");
            ValidateTokenResponse response = new ValidateTokenResponse();
            response.setResponseCode(ErrorCode.BAD_REQUEST);
            response.setResponseMessage(Common.FAIL);
            return response;
        }
    }
}

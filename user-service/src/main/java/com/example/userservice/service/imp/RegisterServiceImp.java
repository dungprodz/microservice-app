package com.example.userservice.service.imp;

import com.example.commonservice.common.CommonException;
import com.example.userservice.entity.RoleEntity;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.entity.UserRoleEntity;
import com.example.userservice.model.requestbody.RegisterRequestBody;
import com.example.userservice.model.responsebody.RegisterResponseBody;
import com.example.userservice.repository.RoleRepository;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.repository.UserRoleRepository;
import com.example.userservice.service.RegisterService;
import com.example.userservice.util.Common;
import com.example.userservice.util.ErrorCode;
import com.example.userservice.util.KMAException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.UUID;

import static com.example.userservice.util.Validate.isPasswordValid;


@Service
@Slf4j
public class RegisterServiceImp implements RegisterService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterServiceImp(UserRepository userRepository, UserRoleRepository userRoleRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<RegisterResponseBody> register(RegisterRequestBody requestBody) throws Exception {
        try {
            log.info("{} register RegisterRequestBody {}", getClass().getSimpleName(), requestBody);
            if (StringUtils.isEmpty(requestBody.getUserName()) || StringUtils.isEmpty(requestBody.getPassWord()) || StringUtils.isEmpty(requestBody.getEmail())
                    || StringUtils.isEmpty(requestBody.getPhoneNumber()) || StringUtils.isEmpty(requestBody.getFullName())) {
                throw new KMAException(ErrorCode.BAD_REQUEST, "BAD_REQUEST");
            }
            if (!isPasswordValid(requestBody.getPassWord())) {
                RegisterResponseBody responseBody =
                        new RegisterResponseBody("mật khẩu không đủ mạnh", ErrorCode.PASS_WORD_NOT_STRONG);
                return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
            }
            if (userRepository.existsByUserName(requestBody.getUserName())) {
                throw new CommonException(ErrorCode.USER_EXITS, "USER_EXITS", HttpStatus.BAD_REQUEST);
            }
            UserEntity user = new UserEntity();

            user.setId(UUID.randomUUID().toString());
            user.setUserName(requestBody.getUserName());
            user.setPassWord(passwordEncoder.encode(requestBody.getPassWord()));
            user.setStatus(Common.ACTIVE);
            user.setEmail(requestBody.getEmail());
            user.setFullName(requestBody.getFullName());
            user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            user.setPhoneNumber(requestBody.getPhoneNumber());
            user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            user.setOtpCount("0");
            userRepository.save(user);

            RoleEntity role = roleRepository.findByRoleName("USER");

            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setId(UUID.randomUUID().toString());
            userRoleEntity.setRoleId(role.getRoleId());
            userRoleEntity.setUserId(user.getId());
            userRoleEntity.setCreatedDate(new Timestamp(System.currentTimeMillis()));
            userRoleEntity.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            userRoleRepository.save(userRoleEntity);
            RegisterResponseBody responseBody =
                    new RegisterResponseBody(Common.SUCCESS, "SUCCESS");
            log.info("{} register responseBody {}", getClass().getSimpleName(), responseBody);
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } catch (KMAException ex) {
            throw ex;
        } catch (Exception e) {
            log.error("{} register Exception {}", getClass().getSimpleName(), e);
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


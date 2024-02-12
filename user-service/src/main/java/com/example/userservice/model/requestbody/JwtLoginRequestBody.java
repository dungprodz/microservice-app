package com.example.userservice.model.requestbody;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class JwtLoginRequestBody implements Serializable {
    @Serial
    private static final long serialVersionUID = 5926468583005150707L;

    private String username;

    private String password;

    public JwtLoginRequestBody(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

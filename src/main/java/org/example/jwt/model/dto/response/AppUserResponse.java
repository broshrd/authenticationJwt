package org.example.jwt.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserResponse {
    private Integer id;
    private String fullName;
    private String email;
    private List<String> roles;
}

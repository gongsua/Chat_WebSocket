package org.example.chatproject.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MeberSaveReqDto {
    private String name;
    private String email;
    private String password;
}

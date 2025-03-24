package org.example.chatproject.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemerListResDto {
    private Long Id;
    private String name;
    private String email;

}

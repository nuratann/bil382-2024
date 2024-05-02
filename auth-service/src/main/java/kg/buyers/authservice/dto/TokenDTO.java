package kg.buyers.authservice.dto;

import lombok.Data;

@Data
public class TokenDTO {
    private String access;
    private String refresh;
}

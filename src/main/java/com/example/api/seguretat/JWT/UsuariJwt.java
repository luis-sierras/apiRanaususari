package com.example.api.seguretat.JWT;

import com.example.api.model.entitats.UsuarioDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UsuariJwt extends UsuarioDTO {
    private String token;

    @Builder(builderMethodName = "jwtUsuariJwtBuilder")
    public UsuariJwt(String username, String avatar, String rol, String token) {
        super(username, avatar, rol);
        this.token = token;
    }
}

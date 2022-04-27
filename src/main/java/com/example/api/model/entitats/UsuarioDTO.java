package com.example.api.model.entitats;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private String username;
    private String avatar;
    private String rol;
}

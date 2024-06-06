package com.fplke.msauthentication.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LoginDto(@NotNull @NotEmpty String username,
                       @NotNull @NotEmpty String password) {
}

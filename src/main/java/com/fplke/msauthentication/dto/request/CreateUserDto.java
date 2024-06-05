package com.fplke.msauthentication.dto.request;

import com.fplke.msauthentication.utils.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateUserDto(@Pattern(regexp = "[A-Za-z0-9_\\s]{5,70}",
                            message = Constants.Strings.INVALID_USERNAME) String username,
                            @Email String email,
                            @NotNull Long teamId,
                            @Pattern(regexp = "[.*]{8,100}", message = Constants.Strings.INVALID_PASSWORD) String password) {
}

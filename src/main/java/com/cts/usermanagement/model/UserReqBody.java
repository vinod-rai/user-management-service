package com.cts.usermanagement.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserReqBody {
    private Long userId;
    @NotEmpty(message = "User name field should be not empty")
    private String userName;
    @NotEmpty(message = "Department field should be not empty")
    private String department;
    @NotEmpty(message = "Manager name field should be not empty")
    private String managerName;
}

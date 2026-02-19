package com.nnk.springboot.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.nnk.springboot.domain.User;

class SecurityUserTest {

    @Test
    void constructor_shouldMapUserFieldsAndPrefixRole() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setUsername("bob");
        user.setPassword("secret");
        user.setRole("ADMIN");

        // Act
        SecurityUser securityUser = new SecurityUser(user);

        // Assert
        assertThat(securityUser.getId()).isEqualTo(1);
        assertThat(securityUser.getUsername()).isEqualTo("bob");
        assertThat(securityUser.getPassword()).isEqualTo("secret");
        assertThat(securityUser.getRole()).isEqualTo("ADMIN");
        assertThat(securityUser.getAuthorities())
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }
}

package com.nnk.springboot.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleIllegalArgument_shouldAddFlashAttributeAndRedirect() {
        // Arrange
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);
        IllegalArgumentException ex = new IllegalArgumentException("entity not found");

        // Act
        String view = handler.handleIllegalArgument(ex, redirectAttributes);

        // Assert
        assertThat(view).isEqualTo("redirect:/");
        verify(redirectAttributes).addFlashAttribute("errorMessage", "entity not found");
    }
}

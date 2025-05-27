package com.desknet.controller;

import com.desknet.dto.request.CategoryRequestDto;
import com.desknet.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.desknet.dto.response.CategoryResponseDto;


import java.util.List;
import java.util.UUID;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    // Mockeamos el service para no usar la base de datos
    @MockitoBean
    private CategoryService categoryService;

    @WithMockUser
    @Test
    void shouldReturn200WhenGettingAllCategories() throws Exception {
        // Simulamos datos de ejemplo
        CategoryResponseDto category1 = new CategoryResponseDto(
                UUID.randomUUID(), "Accesorios", "Cositas de escritorio");
        CategoryResponseDto category2 = new CategoryResponseDto(
                UUID.randomUUID(), "Tecnología", "Cosas tech");

        List<CategoryResponseDto> mockCategories = List.of(category1, category2);

        // Cuando el servicio se llame, devolverá esta lista simulada
        when(categoryService.getAllCategories()).thenReturn(mockCategories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Accesorios"))
                .andExpect(jsonPath("$[1].name").value("Tecnología"));

    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn201WhenPostingNewCategory() throws Exception{

        String categoryJson = """
                {
                    "name":"Clocks",
                    "description":"Clocks for desks"
                }
                """;

        mockMvc.perform(post("/api/categories").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isCreated());
        

        ArgumentCaptor<CategoryRequestDto> captor = ArgumentCaptor.forClass(CategoryRequestDto.class);
        verify(categoryService, times(1)).createCategory(captor.capture());

        CategoryRequestDto dto = captor.getValue();

        assertEquals("Clocks", dto.getName());
        assertEquals("Clocks for desks", dto.getDescription());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenGettingCategory() throws Exception{

        CategoryResponseDto category = new CategoryResponseDto(UUID.randomUUID(), "Keyboards", "Keyboards and keyboards stuffs");

        when(categoryService.getCategoryById(category.getId())).thenReturn(category);

        mockMvc.perform(get("/api/categories/{id}", category.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Keyboards"))
                .andExpect(jsonPath("$.description").value("Keyboards and keyboards stuffs"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenUpdateCategory() throws Exception{

        UUID id = UUID.randomUUID();

        String updatedJson = """
                    {
                        "name":"Hubs",
                        "description":"External ports"
                    }
                """;

        CategoryResponseDto updatedResponse = new CategoryResponseDto(id, "Hubs", "External ports");


        when(categoryService.updateCategoryById(eq(id), any(CategoryRequestDto.class)))
                .thenReturn(updatedResponse);

        mockMvc.perform(put("/api/categories/{id}", id)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(updatedJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Hubs"))
                .andExpect(jsonPath("$.description").value("External ports"));

        ArgumentCaptor<CategoryRequestDto> captor = ArgumentCaptor.forClass(CategoryRequestDto.class);
        verify(categoryService, times(1)).updateCategoryById(eq(id), captor.capture());

        CategoryRequestDto capturedDto = captor.getValue();

        assertEquals("Hubs", capturedDto.getName());
        assertEquals("External ports", capturedDto.getDescription());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn204WhenDeleteCategory() throws Exception{

        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/categories/{id}", id)
                    .with(csrf()))
                .andExpect(status().isNoContent());

        verify(categoryService, times(1)).deleteCategoryById(id);

    }


}

package com.desknet.controller;


import com.desknet.dto.request.ProductRequestDto;
import com.desknet.dto.response.ProductResponseDto;
import com.desknet.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    String apiUrl = "/api/products";

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn201WhenPostingNewProduct() throws Exception {

        UUID categoryId = UUID.randomUUID();

        String productJson = String.format("""
                    {
                        "name":"Red clock",
                        "description":"Classic clock with ring bells",
                        "price":6999,
                        "stock":10,
                        "categoryId":"%s"
                    }
                """, categoryId);

        mockMvc.perform(post(apiUrl).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());

        ArgumentCaptor<ProductRequestDto> captor = ArgumentCaptor.forClass(ProductRequestDto.class);
        Mockito.verify(productService, times(1)).createProduct(captor.capture());

        ProductRequestDto dto = captor.getValue();

        assertEquals("Red clock", dto.getName());
        assertEquals("Classic clock with ring bells", dto.getDescription());
        assertEquals(new BigDecimal(6999), dto.getPrice());
        assertEquals(10, dto.getStock());
        assertEquals(categoryId, dto.getCategoryId());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenGettingAllProducts() throws Exception{

        ProductResponseDto product1 = new ProductResponseDto(UUID.randomUUID(), "Red clock", "Classic clock with ring bells", new BigDecimal(6999), 10, "Clocks");
        ProductResponseDto product2 = new ProductResponseDto(UUID.randomUUID(), "Mouse pad Mickey Mouse", "Mickey mouse print", new BigDecimal(4999), 10, "Mouse Pad");
        List<ProductResponseDto> productList = List.of(product1, product2);

        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(get(apiUrl))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Red clock"))
                .andExpect(jsonPath("$[1].name").value("Mouse pad Mickey Mouse"));

    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenGettingAllProductsByCategory() throws Exception{

        UUID categoryId = UUID.randomUUID();

        ProductResponseDto product1 = new ProductResponseDto(UUID.randomUUID(), "Red clock", "Classic clock with ring bells", new BigDecimal(6999), 10, "Clocks");
        ProductResponseDto product2 = new ProductResponseDto(UUID.randomUUID(), "White clock", "Classic clock with ring bells", new BigDecimal(6999), 10, "Clocks");
        List<ProductResponseDto> productList = List.of(product1, product2);

        when(productService.getProductsByCategory(categoryId)).thenReturn(productList);

        mockMvc.perform(get(apiUrl+"/category/{id}", categoryId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Red clock"))
                .andExpect(jsonPath("$[1].name").value("White clock"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenGettingProductById() throws Exception{

        UUID productId = UUID.randomUUID();
        ProductResponseDto product = new ProductResponseDto(UUID.randomUUID(), "Red clock", "Classic clock with ring bells", new BigDecimal(6999), 10, "Clocks");

        when(productService.getProductById(productId)).thenReturn(product);

        mockMvc.perform(get(apiUrl+"/{id}", productId).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name").value("Red clock"));
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn200WhenUpdateProductById() throws Exception{
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        String productJson = String.format("""
                    {
                        "name":"Ghostface pencil",
                        "description":"Pencil with Ghostface design",
                        "price":4999,
                        "stock":5,
                        "categoryId":"%s"
                    }
                """, categoryId);

        ProductResponseDto productUpdated = new ProductResponseDto(productId, "Ghostface pencil", "Pencil with Ghostface design", new BigDecimal(4999), 5, "pencil");

        when(productService.updateProductById(eq(productId), any(ProductRequestDto.class))).thenReturn(productUpdated);

        mockMvc.perform(put(apiUrl+"/{id}", productId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId.toString()))
                .andExpect(jsonPath("$.name").value("Ghostface pencil"));

        ArgumentCaptor<ProductRequestDto> captor = ArgumentCaptor.forClass(ProductRequestDto.class);
        verify(productService, times(1)).updateProductById(eq(productId), captor.capture());

        ProductRequestDto productCaptor = captor.getValue();
        assertEquals( "Ghostface pencil", productCaptor.getName());
        assertEquals(new BigDecimal(4999), productCaptor.getPrice());

    }

    @WithMockUser(roles = "ADMIN")
    @Test
    void shouldReturn404WhenDeleteProduct() throws Exception{
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete(apiUrl+"/{id}", id).with(csrf()))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProductById(id);
    }

}

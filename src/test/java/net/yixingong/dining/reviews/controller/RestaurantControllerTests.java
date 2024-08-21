package net.yixingong.dining.reviews.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.yixingong.dining.reviews.payload.JWTAuthResponse;
import net.yixingong.dining.reviews.payload.LoginDto;
import net.yixingong.dining.reviews.payload.RestaurantDto;
import net.yixingong.dining.reviews.service.AuthService;
import net.yixingong.dining.reviews.service.RestaurantService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class RestaurantControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    public void givenRestaurantObject_whenAddRestaurant_thenReturnSavedRestaurant() throws Exception {
//
//        RestaurantDto restaurantDto = RestaurantDto.builder()
//                .id(1L)
//                .name("Marzo")
//                .city("Dublin")
//                .phoneNumber("851500000")
//                .website("www.marzo.com")
//                .postcode("D0P799")
//                .build();
//
//        given(restaurantService.addRestaurant(any(RestaurantDto.class)))
//                .willAnswer((invocation) -> invocation.getArgument(0));
//        ResultActions response = mockMvc.perform(post("/api/restaurants")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(restaurantDto)));
//
//        response.andExpect(status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(restaurantDto.getId())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(restaurantDto.getName())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.city", CoreMatchers.is(restaurantDto.getCity())))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", CoreMatchers.is(restaurantDto.getPhoneNumber())));
//    }
}

package com.deliveryfood.controller;

import com.deliveryfood.dto.OrderDto;
import com.deliveryfood.model.OrderInput;
import com.deliveryfood.model.RestaurantInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private OrderController orderController;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @DisplayName("주문을 생성한다.")
    void createOrder() throws Exception {
        mockMvc.perform(post("/orders/checkout"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("주문을 조회한다.")
    void findOrder() throws Exception {
        //given and when
        OrderInput orderInput = OrderInput.builder()
                .orderId("8164faf0-ed43-4d7a-9b1c-e1986df4745e")
                .build();

        //then
        mockMvc.perform(get("/orders/"
                        + orderInput.getOrderId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void findOrderById() throws Exception {
        //given and when
        OrderInput orderInput = OrderInput.builder()
                .userId("222")
                .build();

        //then
        mockMvc.perform(get("/orders/"
                        + orderInput.getUserId()
                        + "/orders")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("주문을 수정한다.")
    void modifyOrderById() throws Exception {
        //given and when
        OrderInput orderInput = OrderInput.builder()
                .orderId("8164faf0-ed43-4d7a-9b1c-e1986df4745e")
                .state("1")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //then
        mockMvc.perform(put("/orders/"
                        + orderInput.getOrderId())
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderInput)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
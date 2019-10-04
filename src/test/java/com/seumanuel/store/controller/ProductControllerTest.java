package com.seumanuel.store.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.seumanuel.store.model.Product;
import com.seumanuel.store.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;

import static com.seumanuel.store.helper.ProductHelper.defaultProductAnswer;
import static com.seumanuel.store.helper.ProductHelper.newProduct;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService service;

    @Test
    public void testFindByIdSuccess() throws Exception {

        //setup
        given(service.findById("123")).willReturn(newProduct("123","Tomate"
                ,"Fruta", new BigDecimal(10), new BigDecimal(10)));

        //exec
        ResultActions ret = mvc.perform(
                get("/product/123")
                        .accept(MediaType.APPLICATION_JSON)
        );

        //assert
        ret
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("123")))
                .andExpect(jsonPath("$.name", is("Tomate")))
                .andExpect(jsonPath("$.description", is("Fruta")))
                .andExpect(jsonPath("$.price", is(10)))
                .andExpect(jsonPath("$.stock",is(10)))

        ;

        verify(service, atLeastOnce()).findById("123");

    }

    @Test
    public void testSaveWithSuccess() throws Exception {
        //setup
        Product ProductToSave = newProduct("1235","Bleach", "Best Drink",
                new BigDecimal(92), new BigDecimal(22));

        given(service.save(any(Product.class))).willAnswer(defaultProductAnswer("1235"));

        String data = mapper.writeValueAsString(ProductToSave);

        //exec
        ResultActions request = mvc.perform(
                post("/product")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //assert - reponse
        request
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1235")))
                .andExpect(jsonPath("$.name", is("Bleach")))
                .andExpect(jsonPath("$.description", is("Best Drink")))
                .andExpect(jsonPath("$.price", is(92)))
                .andExpect(jsonPath("$.stock", is(22)))

        ;


        //assert - behavior
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        verify(service, atLeastOnce()).save(argument.capture());

        Product ret = argument.getValue();

        Assert.assertNotNull(ret.getId());
        Assert.assertEquals("Bleach", ret.getName());
        Assert.assertEquals("Best Drink", ret.getDescription());
        Assert.assertEquals(new BigDecimal(92), ret.getPrice());
        Assert.assertEquals(new BigDecimal(22), ret.getStock());

    }

    @Test
    public void testDeleteSuccess() throws Exception {

        willDoNothing().given(service).delete("1235");

        ResultActions request = mvc.perform(
                delete("/product/123")
        );

        request.andExpect(status().isOk());

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        verify(service,times(1)).delete(argument.capture());
        Assert.assertEquals("123",argument.getValue());
    }

//    @Test
//    public void testUpdateSuccess() throws Exception {
//
//        //setup
//        Product productToSave = newProduct("1235", "Batata", "Podre"
//                , new BigDecimal(2), new BigDecimal(52));
//
//        given(service.update("1235",new BigDecimal(52))).willAnswer(defaultProductAnswer("1235",new BigDecimal(52)));
//
//        String data = mapper.writeValueAsString(productToSave);
//
//        //exec
//        ResultActions request = mvc.perform(
//                put("/product/1235")
//                        .content(data)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        //assert - reponse
//        request
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is("1235")))
//                .andExpect(jsonPath("$.name", is("Batata")))
//                .andExpect(jsonPath("$.description", is("Podre")))
//                .andExpect(jsonPath("$.price", is(new BigDecimal(2))))
//                .andExpect(jsonPath("$.stock", is(new BigDecimal(52))));
//
//        //assert - behavior
//        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
//
//        verify(service, atLeastOnce()).update(argument.capture().getId(),argument.capture().getStock());
//
//        Product ret = argument.getValue();
//
//        Assert.assertEquals("Batata", ret.getName());
//        Assert.assertEquals("Podre", ret.getDescription());
//        Assert.assertEquals(new BigDecimal(2), ret.getPrice());
//        Assert.assertEquals(new BigDecimal(52), ret.getStock());
//    }

}

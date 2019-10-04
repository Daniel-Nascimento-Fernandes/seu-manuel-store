package com.seumanuel.store.service;

import com.seumanuel.store.model.Product;
import com.seumanuel.store.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.*;

import static com.seumanuel.store.helper.ProductHelper.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    private ProductService service;

    @Rule
    public ExpectedException exceptionExpect = ExpectedException.none();

    @Before
    public void setUp() {
        this.service = new ProductService(repository);
    }


    @Test
    public void testProductFindByIdReturningNull() {
        //setup
        given(repository.findById("123")).willReturn(Optional.empty());

        //exec
        Product ret = service.findById("123");

        //assert
        verify(repository, atLeastOnce()).findById("123");
        Assert.assertNull(ret);
    }

    @Test
    public void testProductFindByIdReturningObject() {
        //setup
        given(repository.findById("124")).willReturn(Optional.of(new Product()));

        //exec
        Product ret = service.findById("124");

        //assert
        verify(repository, atLeastOnce()).findById("124");
        Assert.assertNotNull(ret);
    }

    @Test
    public void testProductSaveSuccess() {
        //setup
        given(repository.save(any(Product.class))).willAnswer(defaultProductAnswer("1234"));
        Product productToSave = newProduct("1234", BigDecimal.valueOf(0));

        //exec
        Product ret = service.save(productToSave);

        //assert
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        verify(repository, atLeastOnce()).save(argument.capture());

        Assert.assertEquals("1234", ret.getId());

    }


    @Test
    public void testProductUpdateSuccess() {
        //setup
        given(repository.save(any(Product.class))).willAnswer(defaultProductAnswer("1234",BigDecimal.valueOf(8)));
        given(repository.findById("1234")).willReturn(Optional.of(newProduct("1234",BigDecimal.valueOf(10))));

        //exec
        Product ret = service.update("1234",BigDecimal.valueOf(8));

        //assert
        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

        verify(repository, atLeastOnce()).save(argument.capture());


        Assert.assertEquals("1234", ret.getId());
        Assert.assertEquals(BigDecimal.valueOf(8), ret.getStock());

    }

    @Test
    public void testProductDeleteSuccess() {
        //setup
        willDoNothing().given(repository).deleteById("4321");

        //exec
        service.delete("4321");

        //assert
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

        verify(repository, times(1)).deleteById(argument.capture());
        Assert.assertEquals("4321", argument.getValue());
    }


    @Test
    public void testProductFindByNameReturningObject() {
        //setup
        given(repository.findByNameContaining("batata")).willReturn(newProductListName(3, "batata"));

        //exec
        List<Product> ret = service.findByName("batata");

        //assert
        verify(repository, atLeastOnce()).findByNameContaining("batata");
        Assert.assertNotNull(ret);
        Assert.assertEquals(3,ret.size());
        for (Product p:ret
             ) {
            Assert.assertEquals("batata",p.getName());
        }
        // more asserts to be done?
    }


    @Test
    public void testProductFindByPriceReturningObject() {
        //setup
        given(repository.findByPriceBetween(BigDecimal.valueOf(4),BigDecimal.valueOf(12))).willReturn(newProductListPrice(3, 4,12));

        //exec
        List<Product> ret = service.findByPrice(BigDecimal.valueOf(4),BigDecimal.valueOf(12));

        //assert
        verify(repository, atLeastOnce()).findByPriceBetween(BigDecimal.valueOf(4),BigDecimal.valueOf(12));
        Assert.assertNotNull(ret);
        Assert.assertEquals(3,ret.size());
        for (Product p:ret
        ) {
            Assert.assertTrue(p.getPrice().intValue()>=4);
            Assert.assertTrue(p.getPrice().intValue()<=12);
        }
        // more asserts to be done?
    }

}

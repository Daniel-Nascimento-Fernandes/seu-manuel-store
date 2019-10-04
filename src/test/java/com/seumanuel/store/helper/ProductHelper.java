package com.seumanuel.store.helper;

import com.seumanuel.store.model.Product;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;

public final class ProductHelper {

    private ProductHelper() {
    }

    public static Product newProduct(String id,
            String name,
            String description,
            BigDecimal price,
            BigDecimal stock) {
        Product ret = new Product();
        ret.setId(id);
        ret.setName(name);
        ret.setDescription(description);
        ret.setPrice(price);
        ret.setStock(stock);
        return ret;
    }

    public static Answer<Product> defaultProductAnswer(final String id) {
        return (Answer<Product>) invocationOnMock -> {
            Product arg = invocationOnMock.getArgument(0);
            Product ret = newProduct(id, arg.getName(),
                    arg.getDescription(),
                    arg.getPrice(),
                    arg.getStock());
            ret.setId(id);
            return ret;
        };
    }
}

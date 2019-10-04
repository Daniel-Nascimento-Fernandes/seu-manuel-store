package com.seumanuel.store.helper;

import com.seumanuel.store.model.Product;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static Product newProduct(String id, BigDecimal stock) {
        Product ret = new Product();
        ret.setId(id);
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


    public static Answer<Product> defaultProductAnswer(final String id, final BigDecimal stock) {
        return (Answer<Product>) invocationOnMock -> {
            Product arg = invocationOnMock.getArgument(0);
            Product ret = newProduct(id,stock);
            ret.setId(id);
            ret.setStock(stock);
            return ret;
        };
    }

    public static List<Product> newProductListName(Integer size, String name) {

        return IntStream.range(1, size + 1)
                .mapToObj(i ->
                        newProduct("id" + i, name, "", BigDecimal.valueOf(i), BigDecimal.valueOf(i)))
                .collect(Collectors.toList());

    }

    public static List<Product> newProductListPrice(Integer size, Integer min, Integer max) {

        Random random = new Random(2);

        return IntStream.range(1, size + 1)
                .mapToObj(i ->
                        newProduct("id" + i, "name", "",
                                BigDecimal.valueOf(random.nextInt((max-min)+1) + min),
                                BigDecimal.valueOf(i)
                        ))
                .collect(Collectors.toList());

    }

}

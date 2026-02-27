package com.batch.processing.BatchProcessingSample.config;

import com.batch.processing.BatchProcessingSample.model.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Product, Product> {

    @Override
    public @Nullable Product process(Product item) throws Exception {
        double price = item.getPrice();
        double discount = item.getDiscount();
        double final_price = price - discount;
        item.setDiscounted_price(final_price);

        return item;
    }
}

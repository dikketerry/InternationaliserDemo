package io.eho.internationaliserdemo.controller;

import io.eho.internationaliserdemo.model.Product;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class ProductsController {

    @GetMapping("/index")
    public ModelAndView index() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        List<Product> products = fetchProducts();
        modelAndView.addObject("products", products);

        return modelAndView;
    }


    // dummy method to simulate fetching products from a data source
    private List<Product> fetchProducts() {
        Locale locale = LocaleContextHolder.getLocale();

        List<Product> products = new ArrayList<>();

        Product p = new Product();
        p.setName("television");
        p.setPrice(localizePrice(locale, 1234.56));
        p.setLastUpdated(localizeDate(locale, LocalDate.now()));
        products.add(p);

        p = new Product();
        p.setName("washingmachine");
        p.setPrice(localizePrice(locale, 987.65));
        p.setLastUpdated(localizeDate(locale, LocalDate.now()));
        products.add(p);

        return products;
    }

    private String localizePrice(Locale locale, Double price) {
        NumberFormat nrFormat = NumberFormat.getInstance(locale);
        String localizedPrice = nrFormat.format(price);
        return localizedPrice;
    }

    private String localizeDate(Locale locale, LocalDate date) {
        String localizedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
        return localizedDate;
    }
}

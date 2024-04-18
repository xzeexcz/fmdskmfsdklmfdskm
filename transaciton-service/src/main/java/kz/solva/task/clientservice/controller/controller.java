package kz.solva.task.clientservice.controller;

import kz.solva.task.clientservice.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class controller {

    private final CurrencyService currencyService;
    @GetMapping("/get")
    public String hello() {
        return "Hello,world";
    }
    @PostMapping("/currency")
    public ResponseEntity<? extends Object> getCurrency() throws ExecutionException, InterruptedException {
        return currencyService.addCurrency();
    }
}

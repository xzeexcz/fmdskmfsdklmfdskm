package kz.solva.task.clientservice.service.currency.impl;

import kz.solva.task.clientservice.api.twelvedata.TwelveDataClient;
import kz.solva.task.clientservice.dto.twelvedata.TwelveDataResponse;
import kz.solva.task.clientservice.entity.currency.Currency;
import kz.solva.task.clientservice.mapper.currency.CurrencyMapper;
import kz.solva.task.clientservice.repository.currency.CurrencyRepo;
import kz.solva.task.clientservice.service.currency.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepo currencyRepo;
    private final TwelveDataClient twelveDataClient;
    private final Logger logger = LoggerFactory.getLogger(Currency.class);
    @Override
    public ResponseEntity<? extends Object> addCurrency() throws ExecutionException, InterruptedException {
        List<TwelveDataResponse> responses = twelveDataClient.getQuotes();
        try {
            logger.info("Трансформация в сущность");
            List<Currency> currencyList = currencyMapper.toCurrencyEntityList(responses);
            for(Currency currency : currencyList)
                currency.setId(UUID.randomUUID());
            currencyRepo.saveAll(currencyList);
            logger.info("Стоимость курсов валютных пар успешно добавлена");
            return ResponseEntity.ok("Success");
        } catch (RestClientException e) {
            logger.atWarn().log(e.getMessage());
            return ResponseEntity.ok("Failed");
        }
    }
}
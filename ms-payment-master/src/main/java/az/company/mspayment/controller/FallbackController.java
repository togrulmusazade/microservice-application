package az.company.mspayment.controller;

import az.company.mspayment.client.CountryClient;
import az.company.mspayment.exception.ExceptionResponseFeign;
import az.company.mspayment.model.client.CountryDto;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

@Slf4j
public class FallbackController implements CountryClient {

    private final Exception exception;

    public FallbackController(Exception exception) {
        this.exception = exception;
    }


    @Override
    public List<CountryDto> getAllAvailableCountries(String currency) {
        log.info("THIS Method runned");

        if (exception instanceof TimeoutException) {
            return Collections.emptyList();
        }

        var uuid = UUID.randomUUID().toString();
        throw new ExceptionResponseFeign(uuid, "Service unavailable for now ... please retry after 30 seconds");
    }
}
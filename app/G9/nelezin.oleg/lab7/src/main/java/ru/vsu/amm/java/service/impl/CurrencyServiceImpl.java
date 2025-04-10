    package ru.vsu.amm.java.service.impl;

    import ru.vsu.amm.java.entity.Currency;
    import ru.vsu.amm.java.exception.DataNotFoundException;
    import ru.vsu.amm.java.exception.DatabaseException;
    import ru.vsu.amm.java.repository.CurrencyRepository;
    import ru.vsu.amm.java.service.CurrencyService;

    import java.sql.SQLException;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.logging.Logger;

    public class CurrencyServiceImpl implements CurrencyService {

        private static final Logger log = Logger.getLogger(CurrencyServiceImpl.class.getName());

        private final CurrencyRepository currencyRepository;

        public CurrencyServiceImpl() {
            log.info("call CurrencyServiceImpl constructor");
            currencyRepository = new CurrencyRepository();
        }

        public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
            this.currencyRepository = currencyRepository;
        }

        @Override
        public List<Currency> getAllCurrencies() {
            log.info("call getAllCurrencies");
            List<Currency> currencies = new ArrayList<>();
            try {
                currencies = currencyRepository.findAll();
                log.info("found currencies");
            } catch (SQLException e) {
                log.severe("error DatabaseException");
                throw new DatabaseException(e.getMessage());
            }
            return currencies;
        }

        @Override
        public Currency findByCode(String code) {
            log.info("call findByCode currency");
            try {
                Currency currency = currencyRepository.findByCode(code).orElseThrow(
                        () -> new DataNotFoundException("Не найдена запрашиваемая валюта")
                );
                log.info("found currency");
                return currency;
            } catch (SQLException e) {
                log.severe("error DatabaseException");
                throw new DatabaseException(e.getMessage());
            }
        }
    }

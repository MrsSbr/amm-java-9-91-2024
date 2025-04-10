import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Currency;
import ru.vsu.amm.java.exception.DataNotFoundException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.CurrencyRepository;
import ru.vsu.amm.java.service.CurrencyService;
import ru.vsu.amm.java.service.impl.CurrencyServiceImpl;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CurrencyServiceTest {

    private CurrencyService currencyService;

    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setUp() {
        currencyRepository = mock(CurrencyRepository.class);
        currencyService = new CurrencyServiceImpl(currencyRepository);
    }

    @Test
    void testGetAllCurrencies() throws SQLException {
        Currency usd = new Currency(1L, "USD", "US Dollar", "$");
        Currency eur = new Currency(2L, "EUR", "Euro", "€");

        when(currencyRepository.findAll()).thenReturn(Arrays.asList(usd, eur));

        List<Currency> currencies = currencyService.getAllCurrencies();

        assertEquals(2, currencies.size());
        assertEquals("USD", currencies.get(0).getCode());
        verify(currencyRepository, times(1)).findAll();
    }

    @Test
    void testFindByCode() throws SQLException {
        Currency rub = new Currency(3L, "RUB", "Russian Ruble", "₽");

        when(currencyRepository.findByCode("RUB")).thenReturn(Optional.of(rub));

        Currency found = currencyService.findByCode("RUB");

        assertNotNull(found);
        assertEquals("RUB", found.getCode());
        verify(currencyRepository, times(1)).findByCode("RUB");
    }

    @Test
    void testFindByCode_notFound() throws SQLException {
        when(currencyRepository.findByCode("ABC")).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> currencyService.findByCode("ABC"));
    }

    @Test
    void testFindByCode_throwsDatabaseException() throws SQLException {
        when(currencyRepository.findByCode("USD")).thenThrow(new SQLException("DB error"));

        assertThrows(DatabaseException.class, () -> currencyService.findByCode("USD"));
    }
}

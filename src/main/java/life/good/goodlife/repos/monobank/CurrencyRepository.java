package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findFirstByCurrencyCodeAAndCurrencyCodeBOrderByDateDesc(Integer currencyCodeA, Integer currencyCodeB);

    @Modifying
    @Query(value = "select * from monobank_currency where currency_code_a = ?1 and currency_code_b = ?2 order by date desc limit ?3", nativeQuery = true)
    Currency[] findLimitByCurrencyCodeAAndCurrencyCodeBOrderByDateDesc(Integer currencyCodeA, Integer currencyCodeB, int limit);
}

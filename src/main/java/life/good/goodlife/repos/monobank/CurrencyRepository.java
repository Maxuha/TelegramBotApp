package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Currency findFirstByCurrencyCodeAAndCurrencyCodeBOrderByDateDesc(Integer currencyCodeA, Integer currencyCodeB);
}

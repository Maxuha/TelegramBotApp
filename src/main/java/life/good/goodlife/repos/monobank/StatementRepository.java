package life.good.goodlife.repos.monobank;

import life.good.goodlife.model.monobonk.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatementRepository extends JpaRepository<Statement, String> {
    Statement findByAmount(String id);

    Statement findFirstByAccountIdOrderByTimeDesc(String accountId);
}

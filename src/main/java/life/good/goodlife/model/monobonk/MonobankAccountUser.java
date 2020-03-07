package life.good.goodlife.model.monobonk;

import javax.persistence.*;

@Entity
@Table(name = "monobank_account_user")
public class MonobankAccountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_monobank_id")
    private Long userMonobankId;

    @Column(name = "account_monobank_id")
    private String accountMonobankId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserMonobankId() {
        return userMonobankId;
    }

    public void setUserMonobankId(Long userMonobankId) {
        this.userMonobankId = userMonobankId;
    }

    public String getAccountMonobankId() {
        return accountMonobankId;
    }

    public void setAccountMonobankId(String accountMonobankId) {
        this.accountMonobankId = accountMonobankId;
    }
}

package blog.dongguabai.bytetcc.sample.provider.service.impl;

import blog.dongguabai.bytetcc.sample.provider.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("accountServiceConfirm")
public class AccountServiceConfirm implements IAccountService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void increaseAmount(String acctId, double amount) {
        int value = this.jdbcTemplate.update(
                "update tb_account_one set amount = amount + ?, frozen = frozen - ? where acct_id = ?", amount, amount, acctId);
        System.out.printf("done increase: acct= %s, amount= %7.2f%n", acctId, amount);
    }

    @Transactional
    public void decreaseAmount(String acctId, double amount) {
        int value = this.jdbcTemplate.update("update tb_account_one set frozen = frozen - ? where acct_id = ?", amount, acctId);
        System.out.printf("done decrease: acct= %s, amount= %7.2f%n", acctId, amount);
    }

}

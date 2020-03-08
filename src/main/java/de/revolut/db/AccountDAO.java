package de.revolut.db;

import de.revolut.core.mapper.AccountMapper;
import de.revolut.core.model.Account;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.slf4j.LoggerFactory.getLogger;

@RegisterMapper(AccountMapper.class)
public interface AccountDAO {
    Logger LOGGER = getLogger(AccountDAO.class);

    @SqlUpdate("create table if not exists account (id uuid not null primary key, balance decimal not null, accountHolder varchar(50), currency varchar(5))")
    void createTable();

    @SqlQuery("select * from account")
    List<Account> getAll();

    @SqlQuery("select * from account where id = :id")
    Account getByUuid(@Bind("id") UUID id);

    @SqlUpdate("update account set balance = :balance where ID = :id")
    int updateBalance(@Bind("id") UUID id, @Bind("balance") BigDecimal balance);

    @SqlUpdate("insert into account (id, balance, accountHolder, currency) values (:id, :balance, :accountHolder, :currency)")
    int insert(@BindBean Account account);

    @SqlUpdate("DROP ALL OBJECTS")
    void dropall();

    @Transaction
    default void transfer(Account receiverAccount, Account senderAccount, BigDecimal amount) {

        updateBalance(receiverAccount.getId(), receiverAccount.getBalance().add(amount));
        LOGGER.info("Amount " + amount + " credited to account " + receiverAccount.getId());
        updateBalance(senderAccount.getId(), senderAccount.getBalance().subtract(amount));
        LOGGER.info("Amount " + amount + " debited from account " + senderAccount.getId());
    }
}

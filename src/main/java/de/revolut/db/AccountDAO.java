package de.revolut.db;

import de.revolut.core.model.Account;
import de.revolut.core.mapper.AccountMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RegisterMapper(AccountMapper.class) //TODO: remove?
public interface AccountDAO {

    @SqlUpdate("create table if not exists account (id uuid not null primary key, balance decimal not null)")
    void createTable();//TODO:move to migration

    @SqlQuery("select * from account")
    List<Account> getAll();

    @SqlQuery("select * from account where id = :id")
    Account getByUuid(@Bind("id") UUID id);

    @SqlUpdate("update account set balance = :balance where ID = :id")
    int update(@Bind("id") UUID id, @Bind("balance") BigDecimal balance);

    @SqlUpdate("insert into account (id, balance) values (:id, :balance)")
    int insert(@BindBean Account account);

    @SqlUpdate("DROP ALL OBJECTS")
    void dropall();
}

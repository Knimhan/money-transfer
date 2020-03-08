package de.revolut.core.mapper;

import de.revolut.core.model.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AccountMapper implements ResultSetMapper<Account> {
    public Account map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Account(UUID.fromString(resultSet.getString("id")),
                resultSet.getBigDecimal("balance"),
                resultSet.getString("accountHolder"),
                resultSet.getString("currency"));
    }

}

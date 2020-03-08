package de.revolut;

import de.revolut.core.model.Account;
import de.revolut.core.service.AccountService;
import de.revolut.core.service.MoneyTransferService;
import de.revolut.db.AccountDAO;
import de.revolut.resource.AccountResource;
import de.revolut.resource.MoneyTransferResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.math.BigDecimal;
import java.util.UUID;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "MoneyTransfer";
    }

    @Override
    public void initialize(Bootstrap<MoneyTransferConfiguration> bootstrap) {
    }

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {

        final AccountDAO accountDAO = getAccountDAO(configuration, environment);
        environment.jersey().register(new AccountResource(new AccountService(accountDAO)));
        environment.jersey().register(new MoneyTransferResource(new MoneyTransferService(accountDAO)));
    }

    private AccountDAO getAccountDAO(MoneyTransferConfiguration configuration,
                                     Environment environment) {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");
        final AccountDAO accountDAO = jdbi.onDemand(AccountDAO.class);
        accountDAO.dropall();
        accountDAO.createTable();
        //few accounts are created for testing transfer api directly
        accountDAO.insert(new Account(UUID.fromString("eced4dbb-e8ef-480e-83f9-185b4c0cdfc6"),
                BigDecimal.TEN, "Ben", "EUR"));
        accountDAO.insert(new Account(UUID.fromString("b2310083-9e35-4e88-aa3e-ef8ae6a4ad4c"),
                BigDecimal.TEN, "Jerry", "EUR"));
        return accountDAO;
    }
}

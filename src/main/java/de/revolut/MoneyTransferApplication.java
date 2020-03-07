package de.revolut;

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
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "h2");

        final AccountDAO accountDAO = jdbi.onDemand(AccountDAO.class);
        accountDAO.dropall(); //TODO: delete this
        accountDAO.createTable(); //TODO: delete this migrate

        final AccountService accountService = new AccountService(accountDAO);
        environment.jersey().register(new AccountResource(accountService));

        final MoneyTransferService moneyTransferService = new MoneyTransferService(accountDAO);
        environment.jersey().register(new MoneyTransferResource(moneyTransferService));

    }
}

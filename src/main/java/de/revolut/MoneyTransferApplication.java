package de.revolut;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MoneyTransferApplication extends Application<MoneyTransferConfiguration> {

    public static void main(final String[] args) throws Exception {
        new MoneyTransferApplication().run(args);
    }

    @Override
    public String getName() {
        return "MoneyTransfer";
    }

    @Override
    public void initialize(final Bootstrap<MoneyTransferConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final MoneyTransferConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}

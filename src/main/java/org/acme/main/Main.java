package org.acme.main;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

import java.util.Base64;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.support.ResourceHelper;
import org.jboss.logging.Logger;

@QuarkusMain
public class Main implements QuarkusApplication {

    private final CamelContext camelContext;
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    @Inject
    public Main(CamelContext camelContext) {
        this.camelContext = camelContext;
    }
    public static void main(String... args) {
        Quarkus.run(Main.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        if (args.length == 0) {
            LOG.error("No content specified.");
            return 1;
        }
        String encodedYaml = args[0];
        byte[] decodedYaml = Base64.getDecoder().decode(encodedYaml.getBytes());
        load(camelContext, decodedYaml);
        return 0;
    }
    private void load(CamelContext ctx, byte[] decodedYaml) throws Exception {
        final ExtendedCamelContext extendedCamelContext = ctx.getCamelContextExtension();
        final RoutesLoader loader = extendedCamelContext.getContextPlugin(RoutesLoader.class);
        final Resource resource = ResourceHelper.fromBytes("example.yaml", decodedYaml);
        loader.loadRoutes(resource);
        LOG.info("Routes loaded successfully.");
    }
}

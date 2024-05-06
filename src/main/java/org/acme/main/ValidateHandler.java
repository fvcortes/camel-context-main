package org.acme.main;

import java.util.Base64;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.support.ResourceHelper;
import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("yaml-validate")
public class ValidateHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private final CamelContext camelContext;

    private static final Logger LOG = Logger.getLogger(ValidateHandler.class);

    @Inject
    public ValidateHandler(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        LOG.infof("CamelContext status: %s", camelContext.getStatus());
        LOG.infof("CamelContext has started?: %s", camelContext.isStarted());
        LOG.infof("CamelContext auto start up?: %s", camelContext.isAutoStartup());
        String encodedYaml = event.getBody();
        byte[] decodedYaml = Base64.getDecoder().decode(encodedYaml.getBytes());
        try {
            load(camelContext, decodedYaml);
        } catch (Exception e) {
            LOG.error(e);
        }
        return new APIGatewayProxyResponseEvent();
    }
    private void load(CamelContext ctx, byte[] decodedYaml) throws Exception {
        final ExtendedCamelContext extendedCamelContext = ctx.getCamelContextExtension();
        final RoutesLoader loader = extendedCamelContext.getContextPlugin(RoutesLoader.class);
        final Resource resource = ResourceHelper.fromBytes("example.yaml", decodedYaml);
        loader.loadRoutes(resource);
        LOG.info("Routes loaded successfully.");
    }

}

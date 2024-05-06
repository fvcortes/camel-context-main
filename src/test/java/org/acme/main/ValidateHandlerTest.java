package org.acme.main;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

@QuarkusTest
public class ValidateHandlerTest {


    @Inject
    private ValidateHandler validateHandler;

    @Test
    public void testContextMain() throws Exception {
        String body = Base64.getEncoder().encodeToString(IOUtils.resourceToByteArray("/integration.yaml"));
        assertDoesNotThrow(() -> 
            validateHandler.handleRequest(
                new APIGatewayProxyRequestEvent().withBody(body), null));
    }
}

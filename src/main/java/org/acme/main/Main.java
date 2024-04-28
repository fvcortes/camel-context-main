/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme.main;

import io.quarkus.arc.Arc;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.Base64;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.quarkus.core.CamelRuntime;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.support.ResourceHelper;
import org.jboss.logging.Logger;

@QuarkusMain
public class Main implements QuarkusApplication {

    private static final Logger LOG = Logger.getLogger(Main.class);

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
        CamelRuntime runtime = Arc.container().instance(CamelRuntime.class).get();
        CamelContext ctx = runtime.getCamelContext();
        load(ctx, decodedYaml);
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

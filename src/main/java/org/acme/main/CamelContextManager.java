package org.acme.main;

import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.catalog.RuntimeCamelCatalog;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.engine.DefaultVariableRepositoryFactory;
import org.apache.camel.spi.ComponentNameResolver;
import org.apache.camel.spi.FactoryFinderResolver;
import org.apache.camel.spi.ModelJAXBContextFactory;
import org.apache.camel.spi.PackageScanClassResolver;
import org.apache.camel.spi.VariableRepositoryFactory;
import org.jboss.logging.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class CamelContextManager {

    private final CamelContext camelContext;
    private DefaultCamelContext defaultCamelContext;

    private static final Logger LOG = Logger.getLogger(CamelContextManager.class);

    @Inject
    public CamelContextManager(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @PostConstruct
    private void setupCamelContext() {
        this.defaultCamelContext = new DefaultCamelContext();
        configureCamelContext(defaultCamelContext);
    }

    public DefaultCamelContext getCamelContext() {
        return defaultCamelContext;
    }

    @PreDestroy
    private void cleanUp() throws IOException {
        defaultCamelContext.close();
    }

    private void configureCamelContext(DefaultCamelContext defaultCamelContext) {
        var factoryResolver = camelContext.getCamelContextExtension().getContextPlugin(FactoryFinderResolver.class);
        var runtimeCamelCatalog = camelContext.getCamelContextExtension().getRuntimeCamelCatalog();
        var modelJAXBContextFactory = camelContext.getCamelContextExtension().getContextPlugin(ModelJAXBContextFactory.class);
        var packageScanClassResolver = camelContext.getCamelContextExtension().getContextPlugin(PackageScanClassResolver.class);
        var componentNameResolver = camelContext.getCamelContextExtension().getContextPlugin(ComponentNameResolver.class);
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        final ExtendedCamelContext extendedCamelContext = defaultCamelContext.getCamelContextExtension();
        defaultCamelContext.setApplicationContextClassLoader(tccl);
        defaultCamelContext.setTypeConverterRegistry(camelContext.getTypeConverterRegistry());
        extendedCamelContext.setBootstrapFactoryFinder(camelContext.getCamelContextExtension().getBootstrapFactoryFinder());
        extendedCamelContext.addContextPlugin(FactoryFinderResolver.class, factoryResolver);
        extendedCamelContext.addContextPlugin(RuntimeCamelCatalog.class, runtimeCamelCatalog);
        extendedCamelContext.addContextPlugin(VariableRepositoryFactory.class, new DefaultVariableRepositoryFactory(defaultCamelContext));
        extendedCamelContext.setRegistry(camelContext.getRegistry());
        defaultCamelContext.setLoadTypeConverters(false);
        extendedCamelContext.addContextPlugin(ModelJAXBContextFactory.class,modelJAXBContextFactory);
        extendedCamelContext.addContextPlugin(PackageScanClassResolver.class, packageScanClassResolver);
        defaultCamelContext.build();
        extendedCamelContext.addContextPlugin(ComponentNameResolver.class, componentNameResolver);
        LOG.info("CamelContext configured successfully.");
    }

}

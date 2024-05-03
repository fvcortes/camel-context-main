package org.acme.main;

import java.util.Base64;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.spi.RoutesLoader;
import org.apache.camel.support.ResourceHelper;
import org.jboss.logging.Logger;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.inject.Inject;

@QuarkusMain
public class ContextMain implements QuarkusApplication {

    private final CamelContextManager camelContextManager;

    private static final Logger LOG = Logger.getLogger(ContextMain.class);


    @Inject
    public ContextMain (CamelContextManager camelContextManager) {
        this.camelContextManager = camelContextManager;
    }

    public static void main(String... args) {
        Quarkus.run(ContextMain.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        if (args.length == 0) {
            LOG.error("No content specified.");
            return 1;
        }
        String encodedYaml = args[0];
        byte[] decodedYaml = Base64.getDecoder().decode(encodedYaml.getBytes());
        LOG.infof("Decoded input: %s", new String(decodedYaml));
        load(camelContextManager.getCamelContext(), decodedYaml);
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

/* 
 * 

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

        load( decodedYaml);

        return 0;
    }
    private void load(byte[] decodedYaml) throws Exception {

        var fastContext = Arc.container().instance(CamelContext.class).get();
        var factoryResolver = fastContext.getCamelContextExtension().getContextPlugin(FactoryFinderResolver.class);
        var runtimeCamelCatalog = fastContext.getCamelContextExtension().getRuntimeCamelCatalog();
        var modelJAXBContextFactory = fastContext.getCamelContextExtension().getContextPlugin(ModelJAXBContextFactory.class);
        var packageScanClassResolver = fastContext.getCamelContextExtension().getContextPlugin(PackageScanClassResolver.class);
        var componentNameResolver = fastContext.getCamelContextExtension().getContextPlugin(ComponentNameResolver.class);


        LOG.info("Context: "+fastContext);
        LOG.info("Registry: "+fastContext.getRegistry());
        LOG.info("FactoryResolver: "+fastContext.getCamelContextExtension().getDefaultFactoryFinder());
        LOG.info("Runtime Camel Catalog: "+runtimeCamelCatalog);
        LOG.info("ModelJAXBContextFactory: "+modelJAXBContextFactory);
        LOG.info("PackageScanClassResolver: "+packageScanClassResolver);
        LOG.info("ComponentNameResolver: "+componentNameResolver);
        LOG.info("TEST");

        final DefaultCamelContext ctx = new DefaultCamelContext();

        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        final ExtendedCamelContext extendedCamelContext = ctx.getCamelContextExtension();
        ctx.setApplicationContextClassLoader(tccl);

        final RoutesLoader loader = extendedCamelContext.getContextPlugin(RoutesLoader.class);
        final Resource resource = ResourceHelper.fromBytes("example.yaml", decodedYaml);
//        ctx.setModelReifierFactory(modelReifierFactory);
        ctx.setTypeConverterRegistry(fastContext.getTypeConverterRegistry());
        extendedCamelContext.setRegistry(fastContext.getRegistry());
        extendedCamelContext.setBootstrapFactoryFinder(fastContext.getCamelContextExtension().getBootstrapFactoryFinder());
        extendedCamelContext.addContextPlugin(FactoryFinderResolver.class, factoryResolver);
        extendedCamelContext.addContextPlugin(RuntimeCamelCatalog.class, runtimeCamelCatalog);
        extendedCamelContext.addContextPlugin(VariableRepositoryFactory.class, new DefaultVariableRepositoryFactory(ctx));
        ctx.setLoadTypeConverters(false);
        extendedCamelContext.addContextPlugin(ModelJAXBContextFactory.class,modelJAXBContextFactory);
        extendedCamelContext.addContextPlugin(PackageScanClassResolver.class, packageScanClassResolver);
        ctx.build();
        extendedCamelContext.addContextPlugin(ComponentNameResolver.class, componentNameResolver);


        loader.loadRoutes(resource);
        LOG.info("Routes loaded successfully.");
        ctx.init();
        LOG.info("AQUI.");
    }

    public RuntimeValue<CamelContext> createContext(RuntimeValue<Registry> registry, RuntimeValue<TypeConverterRegistry> typeConverterRegistry, RuntimeValue<ModelJAXBContextFactory> contextFactory, RuntimeValue<ModelToXMLDumper> xmlModelDumper, RuntimeValue<ModelToYAMLDumper> yamlModelDumper, RuntimeValue<FactoryFinderResolver> factoryFinderResolver, RuntimeValue<ComponentNameResolver> componentNameResolver, RuntimeValue<PackageScanClassResolver> packageScanClassResolver, RuntimeValue<ModelReifierFactory> modelReifierFactory, BeanContainer beanContainer, String version, CamelConfig config) {

        FastCamelContext context = new FastCamelContext(version, (ModelToXMLDumper)xmlModelDumper.getValue(), (ModelToYAMLDumper)yamlModelDumper.getValue());
//        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
//        ExtendedCamelContext extendedCamelContext = context.getCamelContextExtension();
//        context.setApplicationContextClassLoader(tccl);
//        extendedCamelContext.addContextPlugin(FactoryFinderResolver.class, (FactoryFinderResolver)factoryFinderResolver.getValue());
//        extendedCamelContext.addContextPlugin(RuntimeCamelCatalog.class, new CamelRuntimeCatalog(config.runtimeCatalog));
//        extendedCamelContext.addContextPlugin(VariableRepositoryFactory.class, new DefaultVariableRepositoryFactory(context));
//        extendedCamelContext.setRegistry((Registry)registry.getValue());
        context.setModelReifierFactory((ModelReifierFactory)modelReifierFactory.getValue());
//        TypeConverterRegistry typeConverterRegistryValue = (TypeConverterRegistry)typeConverterRegistry.getValue();
//        typeConverterRegistryValue.setInjector(new FastTypeConverterInjector(context));
//        context.setTypeConverterRegistry(typeConverterRegistryValue);

//        if (typeConverterRegistryValue instanceof TypeConverter) {
//            context.setTypeConverter((TypeConverter)typeConverterRegistryValue);
//        }

//        context.setLoadTypeConverters(false);
//        extendedCamelContext.addContextPlugin(ModelJAXBContextFactory.class, (ModelJAXBContextFactory)contextFactory.getValue());
//        extendedCamelContext.addContextPlugin(PackageScanClassResolver.class, (PackageScanClassResolver)packageScanClassResolver.getValue());
//        context.build();
//        extendedCamelContext.addContextPlugin(ComponentNameResolver.class, (ComponentNameResolver)componentNameResolver.getValue());
//        ((CamelProducers)beanContainer.beanInstance(CamelProducers.class, new Annotation[0])).setContext(context);
        return new RuntimeValue(context);
    }



}
 */
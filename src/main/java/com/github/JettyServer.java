package com.github;

import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;

public class JettyServer {

    private static final Logger logger = LoggerFactory.getLogger(JettyServer.class);
    private static final int DEFAULT_PORT = 8081;
    private static final String REQUEST_LOG = "d://jetty-yyyy_mm_dd.request.log";
    private static final String CONTEXT_PATH = "/";
    private static final String CONFIG_LOCATION = "com.github.config";
    private static final String MAPPING_URL = "/*";
    //private static final String DEFAULT_PROFILE = "dev";

    public void startJetty(int port) throws Exception {
        logger.debug("Starting server at port {}", port);
        Server server = new Server(port);
        NCSARequestLog requestLog = new NCSARequestLog(REQUEST_LOG);
        requestLog.setAppend(true);
        requestLog.setExtended(false);
        requestLog.setLogTimeZone("GMT");
        requestLog.setLogLatency(true);
        requestLog.setRetainDays(9);

        RequestLogHandler requestLogHandler = new RequestLogHandler();
        requestLogHandler.setRequestLog(requestLog);
=        HandlerList topLevelHandlers = new HandlerList();
        topLevelHandlers.addHandler(requestLogHandler);
        topLevelHandlers.addHandler(getServletContextHandler(getContext()));
        server.setHandler(topLevelHandlers);
        server.start();
        logger.info("Server started at port {}", port);
        server.join();
    }

    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setErrorHandler(null);
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
        contextHandler.addEventListener(new ContextLoaderListener(context));
       // contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
        return contextHandler;
    }

    private static WebApplicationContext getContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation(CONFIG_LOCATION);
        //context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
        return context;
    }
}

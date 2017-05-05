package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tuyuelai on 2017/5/5.
 */
public class Application {
    private static final int DEFAULT_PORT = 8083;
    private static final Logger logger = LoggerFactory.getLogger(Application.class);


    public static void main(String[] args) {
        // 启动 jettyserver
        try{
            new JettyServer().startJetty(getPortFromArgs(args));
        }catch (Exception e){
            logger.error("",e);
        }
    }
    private static int getPortFromArgs(String[] args) {
        if (args.length > 0) {
            try {
                return Integer.valueOf(args[0]);
            } catch (NumberFormatException ignore) {
            }
        }
        logger.debug("No server port configured, falling back to {}", DEFAULT_PORT);
        return DEFAULT_PORT;
    }
}

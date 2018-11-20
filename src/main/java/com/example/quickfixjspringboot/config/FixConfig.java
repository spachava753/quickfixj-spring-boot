package com.example.quickfixjspringboot.config;

import com.example.quickfixjspringboot.fix.ClientFixApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import quickfix.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Configuration
@Slf4j
public class FixConfig {

    private final String fileName = "initiator.cfg";
    @Autowired
    private ClientFixApp application;

    @Bean
    public ThreadedSocketInitiator threadedSocketInitiator(){
        ThreadedSocketInitiator threadedSocketInitiator = null;

        try {
            SessionSettings settings = new SessionSettings(new FileInputStream(fileName));
            MessageStoreFactory storeFactory = new FileStoreFactory(settings);
            LogFactory logFactory = new FileLogFactory(settings);
            MessageFactory messageFactory = new DefaultMessageFactory();
            threadedSocketInitiator = new ThreadedSocketInitiator(application, storeFactory, settings, logFactory, messageFactory);
        } catch (ConfigError configError) {
            configError.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return threadedSocketInitiator;
    }
}

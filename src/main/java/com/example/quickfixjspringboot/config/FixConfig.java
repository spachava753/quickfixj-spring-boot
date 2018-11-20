package com.example.quickfixjspringboot.config;

import com.example.quickfixjspringboot.fix.ClientFixApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import quickfix.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class FixConfig {

    private final String fileName = "initiator.cfg";

    @Autowired
    private ThreadedSocketInitiator threadedSocketInitiator;

    @Autowired
    private ClientFixApp application;

    @EventListener
    private void startFixInitiator (ContextStartedEvent startedEvent){
        try {
            threadedSocketInitiator.start();
            log.info("--------- ThreadedSocketInitiator started ---------");
        } catch (ConfigError configError) {
            configError.printStackTrace();
            log.error("--------- ThreadedSocketInitiator ran into an error ---------");
        }
    }

    @EventListener
    private void logon (ContextRefreshedEvent refreshedEvent){
        if(threadedSocketInitiator.getSessions() != null && threadedSocketInitiator.getSessions().size() > 0) {
            for (SessionID sessionID: threadedSocketInitiator.getSessions()) {
                Session.lookupSession(sessionID).logon();
            }
            log.info("--------- ThreadedSocketInitiator logged on to sessions. Size: " + threadedSocketInitiator.getSessions().size() + " ---------");
        }
    }

    @EventListener
    private void logout (ContextClosedEvent closedEvent){
        if(threadedSocketInitiator.getSessions() != null && threadedSocketInitiator.getSessions().size() > 0) {
            for (SessionID sessionID: threadedSocketInitiator.getSessions()) {
                Session.lookupSession(sessionID).logon();
            }
            log.info("--------- ThreadedSocketInitiator logged out to sessions. Size: " + threadedSocketInitiator.getSessions().size() + " ---------");
        }
    }

    @EventListener
    private void startFixInitiator (ContextStoppedEvent stoppedEvent){
        threadedSocketInitiator.stop();
        log.info("--------- ThreadedSocketInitiator stoppped ---------");
    }

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

    @Scheduled(fixedRate = 5000)
    public void clientStatus(){
        log.info("Client Status | Logged on: {}. Current Time: {}", threadedSocketInitiator.isLoggedOn(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
    }
}

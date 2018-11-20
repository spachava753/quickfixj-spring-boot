package com.example.quickfixjspringboot.fix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import quickfix.*;

@Slf4j
@Component
public class ClientFixApp implements Application {

    @Override
    public void onCreate(SessionID sessionID) {
        log.debug("--------- onCreate ---------");
    }

    @Override
    public void onLogon(SessionID sessionID) {
        log.debug("--------- onLogon ---------");
    }

    @Override
    public void onLogout(SessionID sessionID) {
        log.debug("--------- onLogout ---------");
    }

    @Override
    public void toAdmin(Message message, SessionID sessionID) {
        log.debug("--------- toAdmin ---------");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        log.debug("--------- fromAdmin ---------");
    }

    @Override
    public void toApp(Message message, SessionID sessionID) throws DoNotSend {
        log.debug("--------- toApp ---------");
    }

    @Override
    public void fromApp(Message message, SessionID sessionID) throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        log.debug("--------- fromApp ---------");
    }
}

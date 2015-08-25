package biz.c24.io.lei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class LeiBatchParserService {

    @Autowired
    @Qualifier("leiBatchProcessingGateway")
    LeiBatchGateway leiBatchGateway;


    public void parse(Message<?> message) throws Exception {
        LeiBatchParser batchParser = new LeiBatchParser(leiBatchGateway);
        batchParser.parse(message);
    }

}
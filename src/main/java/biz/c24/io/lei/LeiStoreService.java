package biz.c24.io.lei;

import biz.c24.io.api.C24;
import biz.c24.io.api.data.SimpleDataObject;
import com.hazelcast.config.Config;
import com.hazelcast.core.EntryView;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.plei.prelei_schema.xsd.LEIRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LeiStoreService {

    Logger LOG = LoggerFactory.getLogger(LeiStoreService.class);

    private HazelcastInstance hazelcast;
    IMap<String, SimpleDataObject> map;

    public LeiStoreService() {
        Config config = new Config();
        hazelcast = Hazelcast.newHazelcastInstance(config);
        map = hazelcast.getMap("C24Cache");
    }

    public Message<?> store(Message<?> message) throws IOException {
        LEIRegistration leiRegistration = (LEIRegistration) message.getPayload();
            map.put(leiRegistration.getLegalEntityIdentifier(), C24.toSdo(leiRegistration));

        return message;
    }

    public boolean exists(String leiIdentifier) {

        EntryView value = map.getEntryView(leiIdentifier);
        if(value == null) {
            LOG.info("No LEI found for {}", leiIdentifier);
            return false;
        }
        return true;
    }


}

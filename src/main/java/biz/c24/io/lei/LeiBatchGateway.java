package biz.c24.io.lei;

import org.plei.prelei_schema.xsd.LEIRegistration;
import org.springframework.stereotype.Component;

@Component
public interface LeiBatchGateway {

    void processBatch(LEIRegistration batch);


}
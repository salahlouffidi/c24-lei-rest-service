package biz.c24.io.lei;


import biz.c24.io.api.ParserException;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.ParseListener;
import biz.c24.io.api.presentation.XMLSource;
import org.plei.prelei_schema.xsd.LEIDirectory;
import org.plei.prelei_schema.xsd.LEIDirectoryElement;
import org.plei.prelei_schema.xsd.LEIRegistration;
import org.plei.prelei_schema.xsd.LEIRegistrationsCls;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import java.io.File;
import java.io.FileReader;

public class LeiBatchParser {

    private final static Logger LOG = LoggerFactory.getLogger(LeiBatchParser.class);

    private final LeiBatchGateway leiBatchGateway;

    public LeiBatchParser(LeiBatchGateway gateway) {
        this.leiBatchGateway = gateway;
    }

    Integer batchCount = 0;

    Long startTime;


    public void parse(Message<?> message) throws Exception {
        ((LEIRegistrationsCls)LEIRegistrationsCls.getInstance()).setProcessAsBatch(true);

        XMLSource source = new XMLSource();
        FileReader fr = new FileReader((File) message.getPayload());
        source.setReader(fr);
        source.setParseListener(new LeiParseListener());
        startTime = System.currentTimeMillis();
        LOG.info("Reading Batches from: {}", ((File) message.getPayload()).getName());
        LEIDirectory directory = (LEIDirectory) source.readObject(LEIDirectoryElement.getInstance());
        LOG.info("Finished Batch Parsing. Time to parse: {} ms", (System.currentTimeMillis() - startTime));
        LOG.info("Number of Batch Records Parsed: {} ", batchCount);

    }

    class LeiParseListener implements ParseListener {

        @Override
        public void onStartBatch(Element element) throws ParserException {
        }

        @Override
        public void onEndBatch(Element element) throws ParserException {
        }

        @Override
        public Object onBatchEntryParsed(Object o) throws ParserException {
            if (o instanceof LEIRegistration) {
                LEIRegistration batch = (LEIRegistration)o;
                leiBatchGateway.processBatch((LEIRegistration) o);
                batchCount++;
                if(batchCount % 1000 == 0) {
                    LOG.info("Parsed {} batch records ({} batches per second)", batchCount, batchCount / ((System.currentTimeMillis() - startTime) / 1000));
                }
                return null;
            }
            return o;
        }


        @Override
        public void onBatchEntryFailed(Object o, ParserException e) throws ParserException {
            System.out.println("Batch Entry Failed");
        }

        @Override
        public String generateFailedName(String s) {
            return null;
        }

        @Override
        public boolean isAdditionalBatch(Element element) {
            return false;
        }
    }
}

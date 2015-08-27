package biz.c24.io.lei;

import org.plei.prelei_schema.xsd.sdo.LEIRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value="/registrations", produces = "application/json")
public class LeiController {

    private LeiStoreService leiStoreService;

    @Autowired
    public LeiController(final LeiStoreService leiStoreService) {
        this.leiStoreService = leiStoreService;
    }

    @RequestMapping(value="/{identifier}", method= RequestMethod.GET)
    public @ResponseBody
    LEIRegistration transform(@PathVariable String identifier) {
        return leiStoreService.get(identifier);
    }


}

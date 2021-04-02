package com.github.khw1031.npsscrap.controller;

import btworks.wskit.server.crypto.WCryptoKA;
import com.github.khw1031.npsscrap.domain.DataSet;
import com.github.khw1031.npsscrap.service.SecureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecureController {

    @Autowired
    private SecureService secureService;

    @RequestMapping(value = "/scraping/encParam", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String encParam(@RequestBody final DataSet input) {
        DataSet result = new DataSet();
        String jsonResult = "";
        try {
            result = secureService.getEncParam(input);
            jsonResult = result.toJSON();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return jsonResult;
    }

    @RequestMapping(value="/scraping/decrypt", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String decScrapData(@RequestBody final DataSet input) {
        DataSet result = new DataSet();
        String jsonResult = "";
        try {
            result = secureService.decScrapData(input);
            jsonResult = result.toJSON();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return jsonResult;
    }
}

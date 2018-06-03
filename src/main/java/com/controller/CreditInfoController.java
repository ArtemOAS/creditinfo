package com.controller;

import com.service.CreditInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Artem on 23.07.2017.
 */
@Controller
public class CreditInfoController {

    @Qualifier("creditInfoService")
    @Autowired
    private CreditInfoService creditInfoService;

    @RequestMapping(value = "/")
    public String main() {
        return "index";
    }

    @RequestMapping(value = "/sums", method = RequestMethod.GET)
    public ResponseEntity<?> creditInfo(){
        return creditInfoService.creditInfoForm();
    }
}

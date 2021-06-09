package com.ys.challange.rest.api.controller;

import com.ys.challange.rest.api.model.AccreditationResponse;
import com.ys.challange.rest.api.services.AccreditationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccreditationController {
    @Autowired
    AccreditationServices services;

    @PostMapping(value = "/user/accreditation")
    public AccreditationResponse userAccreditation(@RequestBody String accreditation) throws Exception {
            return services.checkAccreditation(accreditation);
    }

}

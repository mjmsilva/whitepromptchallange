package com.ys.challange.rest.api.util;

import java.io.*;
import java.net.URL;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class JsonValidator {

    public JsonNode checkValidJson(String json) {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream jsonStream = new ByteArrayInputStream(json.getBytes());
        //Check if is a valid JSON
        try {
            // read data from the stream and store it into JsonNode
            return objectMapper.readTree(jsonStream);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    public Boolean checkValidJsonSchema(JsonNode json, URL schemaUrl) throws Exception {

        // create an instance of the JsonSchemaFactory using version flag
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);

        File initialFile = new File(schemaUrl.getPath());
        InputStream schemaStream = new DataInputStream(new FileInputStream(initialFile));

        // get schema from the schemaStream and store it into JsonSchema
        JsonSchema schema = schemaFactory.getSchema(schemaStream);

        // create set of validation message and store result in it
        Set<ValidationMessage> validationResult = schema.validate(json);

        // show the validation errors
        if (validationResult.isEmpty()) {

            // show custom message if there is no validation error
            return true;

        } else {

            for (ValidationMessage vm : validationResult) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, vm.getMessage());
            }

            return false;
        }

    }

}

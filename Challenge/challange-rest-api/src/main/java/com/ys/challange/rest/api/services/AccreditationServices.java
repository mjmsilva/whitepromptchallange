package com.ys.challange.rest.api.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.ys.challange.rest.api.model.AccreditationResponse;
import com.ys.challange.rest.api.model.Document;
import com.ys.challange.rest.api.model.Root;
import com.ys.challange.rest.api.util.JsonValidator;
import com.ys.challange.rest.api.util.Util;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import org.jboss.weld.environment.servlet.test.bootstrap.MyServletContextListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URL;

@Service
public class AccreditationServices {

    @Autowired
    Util util;
    @Autowired
    JsonValidator validator;
    @Autowired
    AccreditationResponse responseModel;

    //Checks if client qualify for the accredited status
    public AccreditationResponse checkAccreditation(String documents) throws Exception {

        Boolean success = this.checkAccreditationJson(documents);
        Boolean accreditation = this.isAccredited(documents);

        responseModel.setSuccess(success);
        responseModel.setAccredited(accreditation);

        return responseModel;
    }

    //check if client is accredited
    private Boolean isAccredited(String documents) {

        int a = (int) (Math.random() * documents.hashCode());

        if (a % 2 == 0)
            return true;
        else
            return false;
    }

    //Checks if submitted files has correct extension
    private Boolean accreditationCheckFileType(String fileName, String mimeType) throws Exception {
        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
        MimeType extension = allTypes.forName(mimeType);

        return util.getExtensionByStringHandling(fileName).equals(extension.getExtension());
    }

    //Check if all fields have been entered
    private void checkAccreditationFields(String json) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Root root = objectMapper.readValue(json, Root.class);

        if (util.isNullOrEmpty(root.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user_id is null or empty");
        }

        if (util.isNullOrEmpty(root.getPayload().getAccreditation_type())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "accreditation_type is null or empty");
        }

        if (root.getPayload().getDocuments().size() == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "documents are null or empty");
        }

        for (int i = 0; i < root.getPayload().getDocuments().size(); i++) {

            Document d = root.getPayload().getDocuments().get(i);
            String docIndex = "Doc[".concat(String.valueOf(i)).concat("]");

            if (util.isNullOrEmpty(d.getName())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, docIndex.concat(" name is null or empty"));
            }
            if (util.isNullOrEmpty(d.getMime_type())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, docIndex.concat(" mime_type is null or empty"));
            }
            if (util.isNullOrEmpty(d.getContent())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, docIndex.concat(" content is null or empty"));
            }
            if (!this.accreditationCheckFileType(d.getName(), d.getMime_type())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, docIndex.concat(" file extension does not match mime type."));
            }

        }

    }

    //Check if entry is a valid Json and matches schema
    private Boolean checkAccreditationJson(String json) throws Exception {

        //Retrieve schema from resources
        URL schemaUrl = MyServletContextListener.class
                .getClassLoader().getResource("schemas/accreditation.json");

        //Check if json is valid
        JsonNode jsonNode = validator.checkValidJson(json);

        //Check if json matches schema
        if (validator.checkValidJsonSchema(jsonNode, schemaUrl) == true) {
            this.checkAccreditationFields(json);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Json does not match schema."));
        }

        return true;
    }

}

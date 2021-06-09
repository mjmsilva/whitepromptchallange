package com.ys.challange.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import static org.hamcrest.Matchers.containsString;
import com.ys.challange.rest.api.model.Root;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class ChallangeRestApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private String json = "{\n" +
            "\"user_id\": \"g8NlYJnk7zK9BlB1J2Ebjs0AkhCTpE1V\",\n" +
            "\"payload\": {\n" +
            "\"accreditation_type\": \"BY_INCOME\",\n" +
            "\"documents\": [{\n" +
            "\"name\": \"2018.pdf\",\n" +
            "\"mime_type\": \"application/pdf\",\n" +
            "\"content\": \"ICAiQC8qIjogWyJzcmMvKiJdCiAgICB9CiAgfQp9Cg==\"\n" +
            "},{\n" +
            "\"name\": \"2019.jpg\",\n" +
            "\"mime_type\": \"image/jpeg\",\n" +
            "\"content\": \"91cy1wcm9taXNlICJeMi4wLjUiCiAgICB0b3Bvc29ydCAiXjIuMC4yIgo=\"\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "}";

    private String jsonInvalid = "{\n" +
            "\"user_id\": \"g8NlYJnk7zK9BlB1J2Ebjs0AkhCTpE1V\",\n" +
            "\"payload\": {\n" +
            "\"accreditation_type\": \"BY_INCOME\",\n" +
            "\"documents\": [{\n" +
            "\"name\": \"2018.pdf\",\n" +
            "\"mime_type\": \"application/pdf\",\n" +
            "\"content\": \"ICAiQC8qIjogWyJzcmMvKiJdCiAgICB9CiAgfQp9Cg==\"\n" +
            "},{\n" +
            "\"name\": \"2019.jpg\",\n" +
            "\"mime_type\": \"image/jpeg\",\n" +
            "\"content\": \"91cy1wcm9taXNlICJeMi4wLjUiCiAgICB0b3Bvc29ydCAiXjIuMC4yIgo=\",\n" +
            "\"fail\": \"fail\"\n" +
            "}\n" +
            "]\n" +
            "}\n" +
            "}";

    @Autowired
    private  ObjectMapper objectMapper;

    private Root loadEntry() throws Exception {

        Root root = objectMapper.readValue(json, Root.class);
        return root;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void checkAccreditationTest_success() throws Exception {

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void checkAccreditationTest_Invalid_Json() throws Exception {

        Root entry = this.loadEntry();
        entry.setPayload(null);

      mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkAccreditationTest_Invalid_user_id() throws Exception {

        Root entry = this.loadEntry();
        entry.setUser_id("");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("user_id is null or empty"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_accreditation_type() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().setAccreditation_type("");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("accreditation_type is null or empty"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_documents() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().getDocuments().clear();
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("documents are null or empty"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_name() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().getDocuments().get(1).setName("");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("name is null or empty"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_mime_type() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().getDocuments().get(1).setMime_type("");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("mime_type is null or empty"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_content() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().getDocuments().get(1).setContent("");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("content is null or empty"))));

    }

    @Test
    public void checkAccreditationTest_NotMatch_file_mime_type() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().getDocuments().get(1).setName("file.xxx");
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("file extension does not match mime type."))));

    }

    @Test
    public void checkAccreditationTest_Invalid_Json_Format() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().setDocuments(null);
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(j))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString(".payload.documents: null found, array expected"))));

    }

    @Test
    public void checkAccreditationTest_Invalid_Json_Schema() throws Exception {

        Root entry = this.loadEntry();
        entry.getPayload().setDocuments(null);
        String j = objectMapper.writeValueAsString(entry);

        mockMvc.perform(post("/user/accreditation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInvalid))
                .andExpect(status().isBadRequest()).andDo(print())
                .andExpect(content().string((containsString("payload.documents[1].fail: is not defined in the schema and the schema does not allow additional properties"))));

    }

}

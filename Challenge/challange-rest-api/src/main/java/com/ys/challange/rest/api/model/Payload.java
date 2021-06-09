package com.ys.challange.rest.api.model;

import lombok.Data;

import java.util.List;

@Data
public class Payload {
    private String accreditation_type;
    private List<Document> documents;
}
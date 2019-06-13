package com.example.hibernatepostgresrabitmq.rabitmq;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PracticalTipMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String text;
    private final int priority;
    private final boolean secret;

    public PracticalTipMessage(
            @JsonProperty("text") String text,
            @JsonProperty("priority") int priority,
            @JsonProperty("secret") boolean secret) {
        this.text = text;
        this.priority = priority;
        this.secret = secret;
    }
}

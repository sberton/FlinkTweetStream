package com.stream.models;

import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonTweetDeserializationSchema implements DeserializationSchema<JsonTweet> {

    static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public JsonTweet deserialize(byte[] message) throws IOException {
        return objectMapper.readValue(message, JsonTweet.class);
    }

    @Override
    public boolean isEndOfStream(JsonTweet nextElement) {
        return false;
    }

    @Override
    public TypeInformation<JsonTweet> getProducedType() {
        return TypeInformation.of(JsonTweet.class);
    }
}

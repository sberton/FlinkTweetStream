package com.stream.beans;

import com.stream.models.JsonTweet;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

import javax.annotation.Nullable;

public class JsonTweetTimestampAssigner implements AssignerWithPunctuatedWatermarks<JsonTweet> {
    @Nullable
    @Override
    public Watermark checkAndGetNextWatermark(JsonTweet lastElement, long extractedTimestamp) {
        return new Watermark(extractedTimestamp - 1500);
    }

    @Override
    public long extractTimestamp(JsonTweet element, long previousElementTimestamp) {
        return element.getCreated_at();
    }
}

package com.stream.beans;

import com.stream.models.JsonTweet;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple4;

import org.apache.flink.util.Collector;

import java.util.Iterator;
import java.util.List;

public class FlatMapTweet implements FlatMapFunction<JsonTweet, Tuple4<String, String, Long, Integer>> {

    @Override
    public void flatMap(JsonTweet ptweet, Collector<Tuple4<String, String, Long, Integer>> out) throws Exception {

        for(String hashtag:ptweet.getHashtags()){
            out.collect(new Tuple4<String, String, Long, Integer>(ptweet.getUsername(), hashtag,ptweet.getCreated_at(), 1));
        }
    }
}

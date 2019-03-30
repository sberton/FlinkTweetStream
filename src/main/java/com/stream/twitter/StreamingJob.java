/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stream.twitter;

import com.stream.beans.FlatMapTweet;
import com.stream.beans.JsonTweetTimestampAssigner;
import com.stream.models.JsonTweet;
import com.stream.models.JsonTweetDeserializationSchema;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.formats.json.JsonNodeDeserializationSchema;
import org.apache.flink.util.Collector;


import java.util.*;

public class StreamingJob {

	private String tuple4;

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
		// parse user parameters
		ParameterTool parameterTool = ParameterTool.fromArgs(args);
		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("group.id","sberton");
		FlinkKafkaConsumer011<JsonTweet> kafkaConsumer = new FlinkKafkaConsumer011<JsonTweet>("twitter-speed-layer"
				, new JsonTweetDeserializationSchema(), properties);
		kafkaConsumer.setStartFromGroupOffsets();
		kafkaConsumer.assignTimestampsAndWatermarks(new JsonTweetTimestampAssigner());
		DataStreamSource<JsonTweet> tweetStream = env.addSource(kafkaConsumer);
		tweetStream.flatMap(new FlatMapTweet())
				/*.map(new MapFunction<Tuple4<String, String, Long, Integer>, Tuple2<String, Integer>>() {
					@Override
					public Tuple2<String, Integer> map(Tuple4<String, String, Long, Integer> value) throws Exception {
						return new Tuple2<>(value.f1,value.f3);
					}
				})*/
				.keyBy(1)
				.window(TumblingEventTimeWindows.of(Time.minutes(5)))
				.sum(3)
				.print();

		env.execute("Flink Streaming Java API Skeleton");
	}
}

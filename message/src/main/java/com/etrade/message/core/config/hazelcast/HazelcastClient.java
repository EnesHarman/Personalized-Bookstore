package com.etrade.message.core.config.hazelcast;

import com.etrade.message.core.config.hazelcast.dto.SegmentCache;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Component;

@Component
public class HazelcastClient {


    public static final String SEGMENTS = "segments";
    private final HazelcastInstance hazelcastInstance
            = Hazelcast.newHazelcastInstance();

    public SegmentCache put(String number, SegmentCache car) {
        IMap<String, SegmentCache> map = hazelcastInstance.getMap(SEGMENTS);

        return map.putIfAbsent(number, car);
    }

    public SegmentCache get(String key) {
        IMap<String, SegmentCache> map = hazelcastInstance.getMap(SEGMENTS);
        return map.get(key);
    }

    public void delete(String key) {
        IMap<String, SegmentCache> map = hazelcastInstance.getMap(SEGMENTS);
        map.delete(key);
    }

}

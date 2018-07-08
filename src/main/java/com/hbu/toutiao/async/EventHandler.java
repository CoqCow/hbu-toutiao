package com.hbu.toutiao.async;

import java.util.List;

/**
 * Created by nowcoder on 2016/7/14.
 */
public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();
}

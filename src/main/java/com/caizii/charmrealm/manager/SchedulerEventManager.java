package com.caizii.charmrealm.manager;

import com.caizii.charmrealm.CharmRealm;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/*

 */
@Getter
@Setter
public class SchedulerEventManager {


    private ArrayBlockingQueue<Event> eventQueue;


    public SchedulerEventManager() {
        eventQueue = new ArrayBlockingQueue<>(10);
    }

    public void receiveEvent() {

        Bukkit.getScheduler().runTask(CharmRealm.getInstance(), () -> {
            Event event;
            while (true) {

                try {
                    event = eventQueue.poll(1, TimeUnit.SECONDS);

                } catch (InterruptedException e) {
                    throw new RuntimeException("BUG");
                }
                if (event == null) {
                    continue;
                }
                Bukkit.getPluginManager().callEvent(event);

            }
        });

    }

    public void offerEvent(Event event) {
        try {
            eventQueue.offer(event, 1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}

package io.smallrye.reactive.messaging.kafka;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.TopicPartition;

@ApplicationScoped
@Named("my-group-starting-on-fifth-fail-until-second-rebalance")
public class StartFromFifthOffsetFromLatestButFailUntilSecondRebalanceConsumerRebalanceListener
        extends StartFromFifthOffsetFromLatestConsumerRebalanceListener {
    private final AtomicBoolean failOnFirstAttempt = new AtomicBoolean(true);

    @Override
    public void onPartitionsAssigned(Consumer<?, ?> consumer,
            Collection<TopicPartition> partitions) {
        if (failOnFirstAttempt.getAndSet(false)) {
            throw new IllegalArgumentException("testing failure");
        } else {
            super.onPartitionsAssigned(consumer, partitions);
        }
    }
}

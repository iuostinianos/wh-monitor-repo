package ellas.wh.messaging.akka;

import akka.actor.typed.ActorSystem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActorSystemWrapper<T> {
    private final ActorSystem<T> actorSystem;

    public void tell(T message) {
        actorSystem.tell(message);
    }

    public void close() {
        actorSystem.log().info("ActorSystem [{}] closed.", actorSystem.name());
        actorSystem.terminate();
    }
}

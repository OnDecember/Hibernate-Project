package org.example.dao;

import org.example.entity.Actor;
import org.hibernate.Session;

public class ActorManager extends EntityManager<Actor> {
    public ActorManager(Session session) {
        super(Actor.class, session);
    }

    public ActorManager() {
        super(Actor.class);
    }
}
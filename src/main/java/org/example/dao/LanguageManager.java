package org.example.dao;

import org.example.entity.Language;
import org.hibernate.Session;

public class LanguageManager extends EntityManager<Language> {
    public LanguageManager(Session session) {
        super(Language.class, session);
    }

    public LanguageManager() {
        super(Language.class);
    }
}
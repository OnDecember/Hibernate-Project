package org.example.dao;

import org.example.entity.Category;
import org.hibernate.Session;

public class CategoryManager extends EntityManager<Category> {
    public CategoryManager(Session session) {
        super(Category.class, session);
    }

    public CategoryManager() {
        super(Category.class);
    }
}
package pl.edu.agh.to.drugstore.model.dao;

import java.util.List;

public interface ObjectDAO<T> {

    List<T> findAll();

    T find(int id);

    void add(T object);

    void delete(int id);

    void update(T object);
}

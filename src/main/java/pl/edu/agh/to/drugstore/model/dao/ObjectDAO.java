package pl.edu.agh.to.drugstore.model.dao;

import java.util.List;

public interface ObjectDAO<T> {

    public List<T> findAll();

    public T find(int id);

    public void add(T object);

    public void delete(int id);

    public void update(T object);
}

package ca.i4s.foodguide.persistence.dao;

import ca.i4s.foodguide.model.FoodGuideModel;

import java.util.Optional;

public interface FoodGuideDao<T extends FoodGuideModel, K extends Object> {

    void save(T model);

    void update(T model);

    void delete(K id);

    Optional<T> get(K id);
}

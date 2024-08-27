package org.springframework.hateoas;

import java.util.ArrayList;
import java.util.List;

public interface RepresentationModelAssembler<T, D extends EntityModel<T>> {
    
    D toModel(T entity);

    default Iterable<D> toCollectionModel(Iterable<? extends T> entities) {
        List<D> models = new ArrayList<>();
        for (T entity : entities) {
            models.add(toModel(entity));
        }
        return models;
    }
}

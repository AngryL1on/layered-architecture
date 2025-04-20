package dev.angryl1on.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для управления продуктами в системе инвентаризации ресторана.
 */
public interface ProductRepository {
    /**
     * Добавляет продукт в репозиторий.
     *
     * @param product продукт для добавления
     */
    void addProduct(Product product);

    /**
     * Получает продукт по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор продукта
     * @return Optional с найденным продуктом или пустой, если продукт не найден
     */
    Optional<Product> getProduct(UUID id);

    /**
     * Возвращает список всех продуктов в репозитории.
     *
     * @return список всех доступных продуктов
     */
    List<Product> findAllProducts();

    /**
     * Удаляет все просроченные продукты из репозитория.
     */
    void removeExpiredProducts();
}

package dev.angryl1on.infrastructure;

import dev.angryl1on.domain.Product;
import dev.angryl1on.domain.ProductRepository;

import java.util.*;

/**
 * Реализация {@link ProductRepository}, хранящая продукты в памяти.
 * <p>Использует внутреннюю {@link HashMap} для хранения продуктов по их UUID.</p>
 */
public class InMemoryProductRepository implements ProductRepository {

    /**
     * Карта для хранения продуктов по их уникальным идентификаторам.
     */
    private final Map<UUID, Product> products = new HashMap<>();

    /**
     * Добавляет продукт в репозиторий. Если продукт с таким же идентификатором
     * уже существует, он будет заменён новым.
     *
     * @param product продукт для добавления
     */
    @Override
    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    /**
     * Возвращает продукт по заданному идентификатору.
     *
     * @param id уникальный идентификатор продукта
     * @return {@link Optional} с найденным продуктом, или {@link Optional#empty()},
     * если продукт с таким ID отсутствует
     */
    @Override
    public Optional<Product> getProduct(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    /**
     * Возвращает список всех продуктов, хранящихся в репозитории.
     * <p>Изменения в возвращённом списке не затрагивают внутренние данные репозитория.</p>
     *
     * @return список всех продуктов
     */
    @Override
    public List<Product> findAllProducts() {
        return new ArrayList<>(products.values());
    }

    /**
     * Удаляет из репозитория все продукты, у которых
     * {@link Product#isExpired()} возвращает {@code true}.
     */
    @Override
    public void removeExpiredProducts() {
        products.values().removeIf(Product::isExpired);
    }
}

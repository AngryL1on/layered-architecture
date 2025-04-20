package dev.angryl1on.application;

import dev.angryl1on.domain.Product;
import dev.angryl1on.domain.ProductRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис для управления продуктами и инвентаризацией ресторана.
 */
public class RestaurantService {
    private final ProductRepository productRepository;

    /**
     * Возвращает продукт по ID или бросает исключение, если не найден.
     *
     * @param id идентификатор продукта
     * @return найденный продукт
     * @throws IllegalArgumentException если продукт не найден
     */
    private Product getProductOrThrow(UUID id) {
        return productRepository.getProduct(id)
                .orElseThrow(() -> new IllegalArgumentException("Продукт с ID " + id + " не найден"));
    }

    /**
     * Создаёт экземпляр RestaurantService.
     *
     * @param productRepository репозиторий для доступа к продуктам
     */
    public RestaurantService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Добавляет новый продукт с указанными параметрами.
     *
     * @param name           название продукта
     * @param expirationDate дата истечения срока годности
     * @param unitMeasure    единица измерения продукта
     * @param quantity       начальное количество продукта
     */
    public void addProduct(String name, LocalDate expirationDate, String unitMeasure, int quantity) {
        Product product = new Product(UUID.randomUUID(), name, expirationDate, unitMeasure, quantity);
        productRepository.addProduct(product);
    }

    /**
     * Использует (списывает) указанное количество продукта.
     *
     * @param id       идентификатор продукта
     * @param quantity количество для использования
     */
    public void useProduct(UUID id, int quantity) {
        Product product = getProductOrThrow(id);
        product.decrementQuantity(quantity);
    }

    /**
     * Удаляет все просроченные продукты из репозитория.
     */
    public void removeExpiredProducts() {
        productRepository.removeExpiredProducts();
    }

    /**
     * Корректирует запас продукта до нового количества.
     *
     * @param id          идентификатор продукта
     * @param newQuantity новое количество продукта
     */
    public void performInventoryCorrection(UUID id, int newQuantity) {
        Product product = getProductOrThrow(id);
        int delta = newQuantity - product.getQuantity();
        if (delta > 0) product.incrementQuantity(delta);
        else if (delta < 0) product.decrementQuantity(-delta);
    }

    /**
     * Возвращает список всех продуктов.
     *
     * @return список продуктов
     */
    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    /**
     * Возвращает продукты с количеством меньше или равным пороговому значению.
     *
     * @param threshold пороговое количество
     * @return список продуктов с критическим уровнем запасов
     */
    public List<Product> getCriticalStockProducts(int threshold) {
        return productRepository.findAllProducts().stream()
                .filter(p -> p.getQuantity() <= threshold)
                .toList();
    }

    /**
     * Получает продукт по его идентификатору.
     *
     * @param id идентификатор продукта
     * @return Optional с продуктом или пустой, если не найден
     */
    public Optional<Product> getProductById(UUID id) {
        return productRepository.getProduct(id);
    }
}

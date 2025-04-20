package dev.angryl1on.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Сущность продукта в системе управления инвентаризацией ресторана.
 */
public class Product {

    private UUID id;
    private String name;
    private LocalDate expirationDate;
    private String unitMeasure;
    private int quantity;

    /**
     * Конструирует новый объект Product.
     *
     * @param id             уникальный идентификатор продукта
     * @param name           название продукта
     * @param expirationDate дата истечения срока годности
     * @param unitMeasure    единица измерения (например, кг, шт)
     * @param quantity       количество продукта
     */
    public Product(UUID id, String name, LocalDate expirationDate, String unitMeasure, int quantity) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
        this.unitMeasure = unitMeasure;
        this.quantity = quantity;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity(int amount) {
        this.quantity += amount;
    }

    /**
     * Уменьшает количество продукта на заданную величину.
     * Выбрасывает IllegalArgumentException, если текущего количества недостаточно.
     *
     * @param amount величина для уменьшения
     * @throws IllegalArgumentException если количество меньше amount
     */
    public void decrementQuantity(int amount) {
        if (this.quantity > amount) this.quantity -= amount;
        else throw new IllegalArgumentException("Not enough quantity of product");
    }

    /**
     * Проверяет, истек ли срок годности продукта по текущей дате.
     *
     * @return true, если срок годности прошел, false иначе
     */
    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", expirationDate=" + expirationDate +
                ", unitMeasure=" + unitMeasure +
                ", quantity=" + quantity +
                '}';
    }
}

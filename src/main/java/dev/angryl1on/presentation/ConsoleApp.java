package dev.angryl1on.presentation;

import dev.angryl1on.application.RestaurantService;
import dev.angryl1on.domain.Product;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ConsoleApp {
    private final RestaurantService restaurantService;
    private final Scanner scanner;

    public ConsoleApp(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            handleChoice(choice);
        } while (choice != 0);
    }

    private void printMenu() {
        System.out.println("\\n=== Управление инвентаризацией ресторана ===");
        System.out.println("1. Добавить продукт");
        System.out.println("2. Использовать продукт");
        System.out.println("3. Списать просроченные продукты");
        System.out.println("4. Корректировка продуктов");
        System.out.println("5. Текущие запасы");
        System.out.println("6. Продукты с критическим уровнем запасов");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> addProduct();
            case 2 -> useProduct();
            case 3 -> {
                restaurantService.removeExpiredProducts();
                System.out.println("Просроченные продукты удалены");
            }
            case 4 -> inventoryCorrection();
            case 5 -> showAllProducts();
            case 6 -> criticalStock();
            case 0 -> System.out.println("Выход..");
            default -> System.out.println("Неверный ввод");
        }
    }

    private void addProduct() {
        System.out.println("Название: ");
        String name = scanner.nextLine();

        System.out.println("Срок годности (ГГГГ-ММ-ДД): ");
        LocalDate expirationDate = LocalDate.parse(scanner.nextLine());

        System.out.println("Единицы меры: ");
        String unitMeasure = scanner.nextLine();

        System.out.println("Количество/объем: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        restaurantService.addProduct(name, expirationDate, unitMeasure, quantity);
    }

    private void useProduct() {
        UUID id = readUUID("ID продукта: ");
        Product product = getProductOrThrow(id);

        System.out.println("Количество продукта: " + product.getQuantity() + " " + product.getUnitMeasure());

        int quantity = readInt("Количество, которое нужно использовать: ");

        try {
            restaurantService.useProduct(id, quantity);
            System.out.println("Продукт использован");
            System.out.println("Оставшееся количество: " + product.getQuantity());
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void inventoryCorrection() {
        UUID id = readUUID("ID продукта: ");
        Product product = getProductOrThrow(id);
        System.out.println("Текущее количество: " + product.getQuantity() + " " + product.getUnitMeasure());
        int quantity = readInt("Новое количество: ");
        try {
            restaurantService.performInventoryCorrection(id, quantity);
            System.out.println("Количество продуктов изменено");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void showAllProducts() {
        List<Product> productsList = restaurantService.getAllProducts();
        productsList.forEach(System.out::println);
    }

    private void criticalStock() {
        int threshold = readInt("Введите пороговое значение: ");

        List<Product> products = restaurantService.getCriticalStockProducts(threshold);
        products.forEach(System.out::println);
    }

    private UUID readUUID(String prompt) {
        System.out.println(prompt);
        return UUID.fromString(scanner.nextLine());
    }

    private int readInt(String prompt) {
        System.out.println(prompt);
        return Integer.parseInt(scanner.nextLine());
    }

    private Product getProductOrThrow(UUID id) {
        return restaurantService.getProductById(id)
            .orElseThrow(() -> new IllegalArgumentException("Продукт с ID " + id + " не найден."));
    }
}

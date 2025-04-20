package dev.angryl1on;

import dev.angryl1on.application.RestaurantService;
import dev.angryl1on.domain.ProductRepository;
import dev.angryl1on.infrastructure.InMemoryProductRepository;
import dev.angryl1on.presentation.ConsoleApp;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ProductRepository productRepository = new InMemoryProductRepository();

        RestaurantService restaurantService = new RestaurantService(productRepository);

        restaurantService.addProduct("Оливковое масло", LocalDate.now().plusDays(180), "л", 20);
        restaurantService.addProduct("Чеснок", LocalDate.now().plusDays(30), "кг", 5);
        restaurantService.addProduct("Помидоры", LocalDate.now().plusDays(5), "кг", 20);
        restaurantService.addProduct("Салат ромэн", LocalDate.now().plusDays(4), "шт", 15);
        restaurantService.addProduct("Куриная грудка", LocalDate.now().plusDays(7), "кг", 25);
        restaurantService.addProduct("Рис", LocalDate.now().plusDays(365), "кг", 50);
        restaurantService.addProduct("Пармезан", LocalDate.now().plusDays(60), "кг", 5);

        ConsoleApp ui = new ConsoleApp(restaurantService);

        ui.start();
    }
}
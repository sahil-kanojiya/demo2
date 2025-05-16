package com.hello.advanced.programs;

//Product Catalog Filter System
//        Requirements:
//        Create a list of products with fields like name, price, and category.
//        Use Lambda and Stream API to filter by category, sort by price, and search by name.
//        Demonstrate use of Predicate, Function, Comparator, and Collectors.

import java.util.*;
import java.util.stream.Collectors;

public class P2_ProductCatalogFilterSystem {

    public static void main(String[] args) {
        List<Product> productList = Arrays.asList(
                new Product(1, "Car Tire", 120, Product.Category.Automotive),
                new Product(2, "Engine Oil", 45, Product.Category.Automotive),
                new Product(3, "Car Battery", 90, Product.Category.Automotive),

                new Product(4, "Shampoo", 15, Product.Category.Beauty),
                new Product(5, "Face Wash", 20, Product.Category.Beauty),
                new Product(6, "Perfume", 60, Product.Category.Beauty),

                new Product(7, "T-Shirt", 25, Product.Category.Clothing),
                new Product(8, "Jeans", 50, Product.Category.Clothing),
                new Product(9, "Jacket", 80, Product.Category.Clothing),
                new Product(10, "Sneakers", 100, Product.Category.Clothing),

                new Product(11, "Smartphone", 799, Product.Category.Electronics),
                new Product(12, "Laptop", 1200, Product.Category.Electronics),
                new Product(13, "Bluetooth Headphones", 150, Product.Category.Electronics),
                new Product(14, "Smartwatch", 200, Product.Category.Electronics),

                new Product(15, "Chocolate Box", 10, Product.Category.Food),
                new Product(16, "Pasta Pack", 5, Product.Category.Food),
                new Product(17, "Olive Oil", 12, Product.Category.Food),
                new Product(18, "Coffee Beans", 18, Product.Category.Food),

                new Product(19, "Office Chair", 150, Product.Category.Furniture),
                new Product(20, "Dining Table", 300, Product.Category.Furniture),
                new Product(21, "Bookshelf", 100, Product.Category.Furniture),
                new Product(22, "Bed Frame", 400, Product.Category.Furniture),

                new Product(23, "Cordless Drill", 99, Product.Category.Tools),
                new Product(24, "Hammer", 20, Product.Category.Tools),
                new Product(25, "Screwdriver Set", 25, Product.Category.Tools),
                new Product(26, "Electric Saw", 150, Product.Category.Tools)
        );

        Scanner scanner = new Scanner(System.in);
        loop:
        while (true) {
            System.out.println(
                    "filter by category press 1.\n" +
                            "sort by price press 2.\n" +
                            "search by name press 3.\n" +
                            "print all list press 4.\n" +
                            "exit press 0"
            );
            System.out.print("Enter num:");
            int num = scanner.nextInt();
            scanner.nextLine();
            switch (num) {
                case 1 -> {
                    System.out.print("available category's are:");
                    for (Product.Category category : Product.Category.values()) {
                        System.out.print(category.name() + "  ");
                    }
                    System.out.println();
                    System.out.print("enter category to search:");
                    String categoryStr = scanner.nextLine();
                    try {
                        Product.Category category = Product.Category.valueOf(categoryStr);
                        List<Product> filteredProductList = productList.stream().filter((prod) -> prod.getCategory().equals(category)).collect(Collectors.toList());
                        System.out.println("filtered products:");
                        filteredProductList.forEach(System.out::println);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid category: " + categoryStr);
                    }
                }
                case 2 -> {
//                    List<Product> sortedProductList = productList.stream().sorted((prod1, prod2) -> Integer.compare(prod1.getPrice(), prod2.getPrice())).collect(Collectors.toList());
                    List<Product> sortedProductList = productList.stream().sorted(Comparator.comparingInt(Product::getPrice)).collect(Collectors.toList());
                    System.out.println("sorted products:");
                    sortedProductList.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("enter name to search:");
                    String productName = scanner.nextLine();
                    Optional<Product> nullableProduct = productList.stream().filter((prod) -> prod.getName().equalsIgnoreCase(productName)).findFirst();
                    nullableProduct.ifPresentOrElse(prod -> System.out.println("product:" + prod), () -> System.out.println("product with name: " + productName + " doesn't exists"));
                }
                case 4 -> {
                    System.out.println("products:");
                    productList.forEach(System.out::println);
                }
                case 0 -> {
                    break loop;
                }
                default -> System.out.println("Invalid input.");
            }
            System.out.println();
        }

    }

}

class Product {

    private int id;
    private String name;
    private int price;
    private Category category;

    enum Category {
        Automotive, Beauty, Clothing, Electronics, Food, Furniture, Tools
    }

    public Product(int id, String name, int price, Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                '}';
    }
}
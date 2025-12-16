import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class ToyStore {
    private static ArrayList<Toy> toys = new ArrayList<>();
    private static ArrayList<Store> stores = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static final String STORES_FILE = "stores.txt";
    private static final String TOYS_FILE = "toys.txt";

    public static void main(String[] args) {
        // Load data from files
        loadDataFromFiles();

        // Main menu loop
        boolean running = true;
        while (running) {
            displayMainMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        searchMenu();
                        break;
                    case 2:
                        addMenu();
                        break;
                    case 3:
                        updateMenu();
                        break;
                    case 4:
                        viewAllMenu();
                        break;
                    case 5:
                        statisticsMenu();
                        break;
                    case 6:
                        System.out.println("\nSaving data to files...");
                        saveDataToFiles();
                        System.out.println("Thank you for using Toy Store Management System!");
                        running = false;
                        break;
                    default:
                        System.out.println("\nInvalid choice! Please enter a number between 1-6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nError: Please enter a valid number!");
            } catch (Exception e) {
                System.out.println("\nAn error occurred: " + e.getMessage());
            }
            
            if (running) {
                System.out.println("\nPress Enter to continue...");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("        TOY STORE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(50));
        System.out.println("1. Search Stores/Toys");
        System.out.println("2. Add Store/Toy");
        System.out.println("3. Update Store/Toy");
        System.out.println("4. View All Stores/Toys");
        System.out.println("5. View Statistics");
        System.out.println("6. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-6): ");
    }

    private static void searchMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("SEARCH MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Search Stores");
        System.out.println("2. Search Toys");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    searchStores();
                    break;
                case 2:
                    searchToys();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void searchStores() {
        System.out.print("\nEnter store city name to search: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            System.out.println("Error: Search term cannot be empty!");
            return;
        }

        ArrayList<Store> results = new ArrayList<>();
        for (Store store : stores) {
            if (store.getCity().toLowerCase().contains(searchTerm)) {
                results.add(store);
            }
        }

        if (results.isEmpty()) {
            System.out.println("\nNo stores found matching: " + searchTerm);
        } else {
            System.out.println("\nFound " + results.size() + " store(s):");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i));
            }
        }
    }

    private static void searchToys() {
        System.out.print("\nEnter toy name to search: ");
        String searchTerm = scanner.nextLine().trim().toLowerCase();
        
        if (searchTerm.isEmpty()) {
            System.out.println("Error: Search term cannot be empty!");
            return;
        }

        ArrayList<Toy> results = new ArrayList<>();
        for (Toy toy : toys) {
            if (toy.getName().toLowerCase().contains(searchTerm)) {
                results.add(toy);
            }
        }

        if (results.isEmpty()) {
            System.out.println("\nNo toys found matching: " + searchTerm);
        } else {
            System.out.println("\nFound " + results.size() + " toy(s):");
            for (int i = 0; i < results.size(); i++) {
                Toy toy = results.get(i);
                System.out.println((i + 1) + ". " + formatToyInfo(toy));
            }
        }
    }

    private static void addMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("ADD MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Add Store");
        System.out.println("2. Add Toy");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    addStore();
                    break;
                case 2:
                    addToy();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void addStore() {
        System.out.println("\n--- Add New Store ---");
        
        System.out.print("Enter city name: ");
        String city = scanner.nextLine().trim();
        if (city.isEmpty()) {
            System.out.println("Error: City name cannot be empty!");
            return;
        }

        System.out.print("Enter store hours (e.g., '9AM - 9PM'): ");
        String hours = scanner.nextLine().trim();
        if (hours.isEmpty()) {
            System.out.println("Error: Store hours cannot be empty!");
            return;
        }

        stores.add(new Store(city, hours));
        System.out.println("\n✓ Store added successfully!");
        System.out.println("New store: " + stores.get(stores.size() - 1));
        saveStoresToFile();
    }

    private static void addToy() {
        System.out.println("\n--- Add New Toy ---");
        
        System.out.print("Enter toy name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Toy name cannot be empty!");
            return;
        }

        boolean isDisney = false;
        System.out.print("Is this a Disney toy? (yes/no): ");
        String disneyInput = scanner.nextLine().trim().toLowerCase();
        if (disneyInput.equals("yes") || disneyInput.equals("y")) {
            isDisney = true;
        } else if (!disneyInput.equals("no") && !disneyInput.equals("n")) {
            System.out.println("Error: Please enter 'yes' or 'no'!");
            return;
        }

        int stock = 0;
        System.out.print("Enter stock quantity: ");
        try {
            stock = Integer.parseInt(scanner.nextLine().trim());
            if (stock < 0) {
                System.out.println("Error: Stock cannot be negative!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid integer for stock!");
            return;
        }

        double price = 0.0;
        System.out.print("Enter price: $");
        try {
            price = Double.parseDouble(scanner.nextLine().trim());
            if (price < 0) {
                System.out.println("Error: Price cannot be negative!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number for price!");
            return;
        }

        boolean blackFriday = false;
        System.out.print("Is this eligible for Black Friday sales? (yes/no): ");
        String blackFridayInput = scanner.nextLine().trim().toLowerCase();
        if (blackFridayInput.equals("yes") || blackFridayInput.equals("y")) {
            blackFriday = true;
        } else if (!blackFridayInput.equals("no") && !blackFridayInput.equals("n")) {
            System.out.println("Error: Please enter 'yes' or 'no'!");
            return;
        }

        toys.add(new Toy(name, isDisney, stock, price, blackFriday));
        System.out.println("\n✓ Toy added successfully!");
        System.out.println("New toy: " + formatToyInfo(toys.get(toys.size() - 1)));
    }

    private static void updateMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("UPDATE MENU");
        System.out.println("-".repeat(50));
        System.out.println("1. Update Store");
        System.out.println("2. Update Toy");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    updateStore();
                    break;
                case 2:
                    updateToy();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void updateStore() {
        if (stores.isEmpty()) {
            System.out.println("\nNo stores available to update!");
            return;
        }

        System.out.println("\n--- Available Stores ---");
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i));
        }

        System.out.print("\nEnter store number to update: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index < 0 || index >= stores.size()) {
                System.out.println("Error: Invalid store number!");
                return;
            }

            Store store = stores.get(index);
            System.out.println("\nCurrent store: " + store);
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. City");
            System.out.println("2. Hours");
            System.out.println("3. Both");
            System.out.print("Enter choice (1-3): ");

            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    System.out.print("Enter new city name: ");
                    String city = scanner.nextLine().trim();
                    if (city.isEmpty()) {
                        System.out.println("Error: City name cannot be empty!");
                        return;
                    }
                    store.setCity(city);
                    System.out.println("✓ City updated successfully!");
                    saveStoresToFile();
                    break;
                case 2:
                    System.out.print("Enter new hours: ");
                    String hours = scanner.nextLine().trim();
                    if (hours.isEmpty()) {
                        System.out.println("Error: Hours cannot be empty!");
                        return;
                    }
                    store.setHours(hours);
                    System.out.println("✓ Hours updated successfully!");
                    saveStoresToFile();
                    break;
                case 3:
                    System.out.print("Enter new city name: ");
                    city = scanner.nextLine().trim();
                    if (city.isEmpty()) {
                        System.out.println("Error: City name cannot be empty!");
                        return;
                    }
                    System.out.print("Enter new hours: ");
                    hours = scanner.nextLine().trim();
                    if (hours.isEmpty()) {
                        System.out.println("Error: Hours cannot be empty!");
                        return;
                    }
                    store.setCity(city);
                    store.setHours(hours);
                    System.out.println("✓ Store updated successfully!");
                    saveStoresToFile();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void updateToy() {
        if (toys.isEmpty()) {
            System.out.println("\nNo toys available to update!");
            return;
        }

        System.out.println("\n--- Available Toys ---");
        for (int i = 0; i < toys.size(); i++) {
            System.out.println((i + 1) + ". " + formatToyInfo(toys.get(i)));
        }

        System.out.print("\nEnter toy number to update: ");
        try {
            int index = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (index < 0 || index >= toys.size()) {
                System.out.println("Error: Invalid toy number!");
                return;
            }

            Toy toy = toys.get(index);
            System.out.println("\nCurrent toy: " + formatToyInfo(toy));
            System.out.println("\nWhat would you like to update?");
            System.out.println("1. Name");
            System.out.println("2. Disney Status");
            System.out.println("3. Record Sale (Update Stock)");
            System.out.println("4. Price");
            System.out.println("5. Black Friday Status");
            System.out.print("Enter choice (1-5): ");

            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    System.out.print("Enter new name: ");
                    String name = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Error: Name cannot be empty!");
                        return;
                    }
                    toy.setName(name);
                    System.out.println("✓ Name updated successfully!");
                    saveToysToFile();
                    break;
                case 2:
                    System.out.print("Is this a Disney toy? (yes/no): ");
                    String disneyInput = scanner.nextLine().trim().toLowerCase();
                    if (disneyInput.equals("yes") || disneyInput.equals("y")) {
                        toy.setIsDisney(true);
                        System.out.println("✓ Disney status updated to: Yes");
                    } else if (disneyInput.equals("no") || disneyInput.equals("n")) {
                        toy.setIsDisney(false);
                        System.out.println("✓ Disney status updated to: No");
                    } else {
                        System.out.println("Error: Please enter 'yes' or 'no'!");
                        return;
                    }
                    saveToysToFile();
                    break;
                case 3:
                    System.out.println("\nCurrent stock: " + toy.getStock());
                    System.out.print("Enter quantity sold: ");
                    try {
                        int quantitySold = Integer.parseInt(scanner.nextLine().trim());
                        if (quantitySold < 0) {
                            System.out.println("Error: Quantity sold cannot be negative!");
                            return;
                        }
                        if (quantitySold == 0) {
                            System.out.println("Error: Quantity sold must be greater than 0!");
                            return;
                        }
                        if (quantitySold > toy.getStock()) {
                            System.out.println("Error: Not enough stock! Only " + toy.getStock() + " items available.");
                            return;
                        }
                        
                        // Calculate profit before updating stock
                        double profit = quantitySold * toy.getPrice();
                        
                        // Update stock by subtracting sold quantity
                        int newStock = toy.getStock() - quantitySold;
                        toy.setStock(newStock);
                        
                        System.out.println("✓ Sale recorded successfully!");
                        System.out.println("  Items sold: " + quantitySold);
                        System.out.println("  New stock: " + newStock);
                        System.out.println("  Transaction profit: $" + String.format("%.2f", profit));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid integer!");
                        return;
                    }
                    saveToysToFile();
                    break;
                case 4:
                    System.out.print("Enter new price: $");
                    try {
                        double price = Double.parseDouble(scanner.nextLine().trim());
                        if (price < 0) {
                            System.out.println("Error: Price cannot be negative!");
                            return;
                        }
                        toy.setPrice(price);
                        System.out.println("✓ Price updated successfully!");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number!");
                        return;
                    }
                    saveToysToFile();
                    break;
                case 5:
                    System.out.print("Is this eligible for Black Friday sales? (yes/no): ");
                    String blackFridayInput = scanner.nextLine().trim().toLowerCase();
                    if (blackFridayInput.equals("yes") || blackFridayInput.equals("y")) {
                        toy.setBlackFriday(true);
                        System.out.println("✓ Black Friday status updated to: Yes");
                    } else if (blackFridayInput.equals("no") || blackFridayInput.equals("n")) {
                        toy.setBlackFriday(false);
                        System.out.println("✓ Black Friday status updated to: No");
                    } else {
                        System.out.println("Error: Please enter 'yes' or 'no'!");
                        return;
                    }
                    saveToysToFile();
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void viewAllMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("VIEW ALL");
        System.out.println("-".repeat(50));
        System.out.println("1. View All Stores");
        System.out.println("2. View All Toys");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice (1-3): ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            switch (choice) {
                case 1:
                    viewAllStores();
                    break;
                case 2:
                    viewAllToys();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }

    private static void viewAllStores() {
        if (stores.isEmpty()) {
            System.out.println("\nNo stores available.");
            return;
        }
        System.out.println("\n--- All Stores (" + stores.size() + ") ---");
        for (int i = 0; i < stores.size(); i++) {
            System.out.println((i + 1) + ". " + stores.get(i));
        }
    }

    private static void viewAllToys() {
        if (toys.isEmpty()) {
            System.out.println("\nNo toys available.");
            return;
        }
        System.out.println("\n--- All Toys (" + toys.size() + ") ---");
        for (int i = 0; i < toys.size(); i++) {
            System.out.println((i + 1) + ". " + formatToyInfo(toys.get(i)));
        }
    }

    private static void statisticsMenu() {
        System.out.println("\n" + "-".repeat(50));
        System.out.println("STATISTICS");
        System.out.println("-".repeat(50));
        System.out.println("Total Stores: " + stores.size());
        System.out.println("Total Toys: " + toys.size());
        System.out.println("Total Inventory: " + Inventory(toys));
        System.out.println("Total Full-Price Profit: $" + String.format("%.2f", Profits(toys)));
        
        // Calculate sale profit
        double saleProfit = SalesProfits(toys);
        System.out.println("Total Sale Profit (40% off eligible): $" + String.format("%.2f", saleProfit));
        
        int disneyCount = 0;
        int blackFridayCount = 0;
        for (Toy toy : toys) {
            if (toy.getIsDisney()) disneyCount++;
            if (toy.getBlackFriday()) blackFridayCount++;
        }
        System.out.println("Disney Toys: " + disneyCount);
        System.out.println("Black Friday Eligible: " + blackFridayCount);
    }

    private static String formatToyInfo(Toy toy) {
        return String.format("%s | Disney: %s | Stock: %d | Price: $%.2f | Black Friday: %s",
            toy.getName(),
            toy.getIsDisney() ? "Yes" : "No",
            toy.getStock(),
            toy.getPrice(),
            toy.getBlackFriday() ? "Yes" : "No");
    }

    // Load data from text files
    private static void loadDataFromFiles() {
        loadStoresFromFile();
        loadToysFromFile();
        
        // If files don't exist or are empty, create default data
        if (stores.isEmpty()) {
            System.out.println("No store data files found. Creating default store data files...");
            initializeDefaultStores();
            saveDataToFiles();
        }
        if (toys.isEmpty()) {
            System.out.println("No toy data files found. Creating default toy data files...");
            initializeDefaultToys();
            saveDataToFiles();
        }
        
        System.out.println("Data loaded successfully!");
        System.out.println("Loaded " + stores.size() + " store(s) and " + toys.size() + " toy(s).");
        
    }

    // Load stores from stores.txt file
    // Format: city|hours
    private static void loadStoresFromFile() {
        File file = new File(STORES_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String city = parts[0].trim();
                    String hours = parts[1].trim();
                    if (!city.isEmpty() && !hours.isEmpty()) {
                        stores.add(new Store(city, hours));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading stores file: " + e.getMessage());
        }
    }

    // Load toys from toys.txt file
    // Format: name|isDisney|stock|price|blackFriday
    private static void loadToysFromFile() {
        File file = new File(TOYS_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue; // Skip empty lines and comments
                }
                
                String[] parts = line.split("\\|");
                if (parts.length == 5) {
                    try {
                        String name = parts[0].trim();
                        boolean isDisney = Boolean.parseBoolean(parts[1].trim());
                        int stock = Integer.parseInt(parts[2].trim());
                        double price = Double.parseDouble(parts[3].trim());
                        boolean blackFriday = Boolean.parseBoolean(parts[4].trim());
                        
                        if (!name.isEmpty() && stock >= 0 && price >= 0) {
                            toys.add(new Toy(name, isDisney, stock, price, blackFriday));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Warning: Skipping invalid toy line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading toys file: " + e.getMessage());
        }
    }

    // Save all data to files
    private static void saveDataToFiles() {
        saveStoresToFile();
        saveToysToFile();
    }

    // Save stores to stores.txt file
    private static void saveStoresToFile() {
        try (FileWriter writer = new FileWriter(STORES_FILE)) {
            writer.write("# Stores File Format: city|hours\n");
            writer.write("# Each line represents one store\n");
            writer.write("# Lines starting with # are comments and will be ignored\n\n");
            
            for (Store store : stores) {
                writer.write(store.getCity() + "|" + store.getHours() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving stores file: " + e.getMessage());
        }
    }

    // Save toys to toys.txt file
    private static void saveToysToFile() {
        try (FileWriter writer = new FileWriter(TOYS_FILE)) {
            writer.write("# Toys File Format: name|isDisney|stock|price|blackFriday\n");
            writer.write("# Each line represents one toy\n");
            writer.write("# isDisney and blackFriday should be 'true' or 'false'\n");
            writer.write("# Lines starting with # are comments and will be ignored\n\n");
            
            for (Toy toy : toys) {
                writer.write(toy.getName() + "|" + 
                           toy.getIsDisney() + "|" + 
                           toy.getStock() + "|" + 
                           toy.getPrice() + "|" + 
                           toy.getBlackFriday() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving toys file: " + e.getMessage());
        }
    }

    // Initialize default data (only used if files don't exist)
    private static void initializeDefaultStores() {
        // Initialize default stores
        stores.add(new Store("Los Angeles", "9AM - 9PM"));
        stores.add(new Store("San Diego", "10AM - 8PM"));
        stores.add(new Store("Phoenix", "9AM - 10PM"));
        stores.add(new Store("Las Vegas", "24 Hours"));
        stores.add(new Store("San Jose", "9AM - 7PM"));
    }
    public static void initializeDefaultToys(){
        // Initialize default toys
        toys.add(new Toy("Buzz Lightyear", true, 20, 34.99, true));
        toys.add(new Toy("Hot Wheels", false, 50, 12.99, false));
        toys.add(new Toy("Lego Set", false, 15, 89.99, true));
        toys.add(new Toy("Barbie Doll", false, 10, 49.99, false));
        toys.add(new Toy("Mickey Mouse Plushie", true, 35, 19.99, true));
        toys.add(new Toy("Nerf Gun", false, 40, 39.99, false));
        toys.add(new Toy("Elsa Doll", true, 18, 29.99, true));
        toys.add(new Toy("RC Car", false, 22, 24.99, false));
    }
    // Total Inventory
    public static int Inventory(ArrayList<Toy> toys) {
        int total = 0;
        for (Toy t : toys) total += t.getStock();
        return total;
    }

    // Total full-price profit before any discounts
    public static double Profits(ArrayList<Toy> toys) {
        double total = 0;
        for (Toy t : toys) {
            total += t.getStock() * t.getPrice();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    // Total profit after applying Black Friday discounts (40% off)
    public static double SalesProfits(ArrayList<Toy> toys) {
        double total = 0;
        for (Toy t : toys) {
            double price = t.getPrice();
            if (t.getBlackFriday()) {
                price *= 0.6;
            }
            total += t.getStock() * price;
        }
        return Math.round(total * 100.0) / 100.0;
    }
}
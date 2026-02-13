package questions.lld;

import questions.lld.VendingMachine.Product;
import questions.lld.VendingMachine.VendingMachine;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Vending Machine Demo ===\n");

        VendingMachine vm = new VendingMachine(3, 3);

        // Stock products
        vm.stockProduct("A1", new Product("Coca-Cola", 1.50), 5);
        vm.stockProduct("A2", new Product("Pepsi", 1.50), 3);
        vm.stockProduct("A3", new Product("Water", 1.00), 10);
        vm.stockProduct("B1", new Product("Chips", 2.00), 4);
        vm.stockProduct("B2", new Product("Candy Bar", 1.75), 6);
        vm.stockProduct("B3", new Product("Gum", 0.75), 8);

        vm.displayInventory();

        // Successful purchase
        System.out.println("--- Purchase: Coca-Cola ---");
        vm.insertMoney(2.00);
        vm.selectProduct("A1");

        // Purchase with exact change
        System.out.println("\n--- Purchase: Gum ---");
        vm.insertMoney(0.75);
        vm.selectProduct("B3");

        // Insufficient funds
        System.out.println("\n--- Insufficient funds ---");
        vm.insertMoney(1.00);
        vm.selectProduct("B1"); // needs $2.00

        // Add more money
        vm.insertMoney(1.50);
        vm.selectProduct("B1");

        // Cancel transaction
        System.out.println("\n--- Cancel transaction ---");
        vm.insertMoney(5.00);
        vm.cancelTransaction();

        // Select without money
        System.out.println("\n--- Select without money ---");
        vm.selectProduct("A2");

        vm.displayInventory();
    }
}
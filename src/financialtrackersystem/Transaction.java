package financialtrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Transaction {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void transactionConfig() {
        int option = 0;
        do {
            try {
                System.out.println("\n--- Transaction Menu ---");
                System.out.println("1. Add Transaction");
                System.out.println("2. View Transactions");
                System.out.println("3. Edit Transaction");
                System.out.println("4. Delete Transaction");
                System.out.println("5. Exit");
                
                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addTransaction();
                        break;
                    case 2:
                        viewTransactions();
                        break;
                    case 3:
                        editTransaction();
                        break;
                    case 4:
                        deleteTransaction();
                        break;
                    case 5:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void addTransaction() {
        System.out.println("Enter Transaction Details:");
        
        int userAccountId;
        do {
            System.out.print("\nUser Account ID: ");
            userAccountId = scan.nextInt();
            if (!conf.doesIDExist("user_account", userAccountId)) {
                System.out.println("User Account ID doesn't exist.");
            }
        } while (!conf.doesIDExist("user_account", userAccountId));

        scan.nextLine(); 
        System.out.print("Date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        System.out.print("Description: ");
        String description = scan.nextLine();
        System.out.print("Category: ");
        String category = scan.nextLine();  
        System.out.print("Amount: ");
        double amount = scan.nextDouble();
        scan.nextLine(); 
        System.out.print("Type (income/expense): ");
        String type = scan.nextLine();

        String sql = "INSERT INTO [transaction] (user_account_id, date, description, category, amount, type) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addRecord(sql, userAccountId, date, description, category, amount, type);
    }

    public void viewTransactions() {
        String query = "SELECT * FROM [transaction]";
        String[] headers = {"ID", "User Account ID", "Date", "Description", "Category", "Amount", "Type"};
        String[] columns = {"id", "user_account_id", "date", "description", "category", "amount", "type"};
        conf.viewRecords(query, headers, columns);
    }

    private void editTransaction() {
        System.out.print("\nEnter Transaction ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("[transaction]", id)) {
            System.out.println("Transaction ID not found.");
            return;
        }

        System.out.println("\nEnter New Transaction Details:");
        int userAccountId;
        do {
            System.out.print("New User Account ID: ");
            userAccountId = scan.nextInt();
            if (!conf.doesIDExist("user_account", userAccountId)) {
                System.out.println("User Account ID doesn't exist.");
            }
        } while (!conf.doesIDExist("user_account", userAccountId));

        scan.nextLine(); 
        System.out.print("New Date (YYYY-MM-DD): ");
        String date = scan.nextLine();
        System.out.print("New Description: ");
        String description = scan.nextLine();
        System.out.print("New Category: ");
        String category = scan.nextLine();
        System.out.print("New Amount: ");
        double amount = scan.nextDouble();
        scan.nextLine(); 
        System.out.print("New Type (income/expense): ");
        String type = scan.nextLine();

        String sql = "UPDATE [transaction] SET user_account_id = ?, date = ?, description = ?, category = ?, amount = ?, type = ? WHERE id = ?";
        conf.updateRecord(sql, userAccountId, date, description, category, amount, type, id);
    }

    private void deleteTransaction() {
        System.out.print("Enter Transaction ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM [transaction] WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}


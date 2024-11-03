package financialtrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class FinancialTrackerSystem {
    static Config conf = new Config();
    
    static Scanner scan = new Scanner(System.in);
    static UserAccount userAccount = new UserAccount();
    static Transaction transaction = new Transaction();
    
    public static void main(String[] args) {
        int choice;

        do {
            try {
                System.out.println("\n   + Financial Tracker System +\n");
                System.out.println("1. User Accounts");
                System.out.println("2. Transactions");
                System.out.println("3. Generate Reports");
                System.out.println("4. Exit");
                
                System.out.print("\nEnter Option: ");
                choice = scan.nextInt();
                scan.nextLine();
                System.out.println("");

                switch (choice) {
                    case 1:  
                        userAccount.userAccountConfig();
                        break;
                    case 2:
                        transaction.transactionConfig();
                        break;
                    case 3:                      
                        generateReports();
                        break;
                    case 4:                      
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid Option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                choice = -1;
            }
        } while (choice != 4);
    }

    static void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        
        int userId;
        do {
            System.out.print("Enter User Account ID for report: ");
            userId = scan.nextInt();
            if (!conf.doesIDExist("user_account", userId)) {
                System.out.println("User Account ID doesn't exist.");
            }
        } while (!conf.doesIDExist("user_account", userId));
        
        System.out.println("======================================================================");
        System.out.println("\nTransaction Report for User Account ID: " + userId);
        
        String sql = "SELECT * FROM transaction WHERE user_account_id = " + userId;
        String[] headers = {"ID", "User Account ID", "Date", "Description", "Category", "Amount", "Type"};
        String[] columns = {"id", "user_account_id", "date", "description", "category", "amount", "type"};
        
        conf.viewRecords(sql, headers, columns);
        System.out.println("======================================================================");
    }
}


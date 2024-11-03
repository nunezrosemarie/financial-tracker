package financialtrackersystem;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserAccount {
    Scanner scan = new Scanner(System.in);
    Config conf = new Config();
    
    public void userAccountConfig() {
        int option;
        do {
            try {
                System.out.println("\n--- User Account Menu ---");
                System.out.println("1. Add User Account");
                System.out.println("2. View User Accounts");
                System.out.println("3. Edit User Account");
                System.out.println("4. Delete User Account");
                System.out.println("5. Exit");

                System.out.print("Choose an option: ");
                option = scan.nextInt();
                scan.nextLine();

                switch (option) {
                    case 1:
                        addUserAccount();
                        break;
                    case 2:
                        viewUserAccounts();
                        break;
                    case 3:
                        editUserAccount();
                        break;
                    case 4:
                        deleteUserAccount();
                        break;
                    case 5:
                        System.out.println("Returning to main menu...");
                        break;
                    default:
                        System.out.println("Invalid option.");
                        scan.nextLine();
                        option = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scan.nextLine();
                option = -1;
            }
        } while (option != 5);
    }

    private void addUserAccount() {
        System.out.println("Enter User Account Details:");
        System.out.print("\nName: ");
        String name = scan.nextLine();
        System.out.print("Email: ");
        String email = scan.nextLine();
        System.out.print("Contact Info: ");
        String contactInfo = scan.nextLine();
        System.out.print("Account Name: ");
        String accountName = scan.nextLine();
        System.out.print("Account Type: ");
        String accountType = scan.nextLine();

        String sql = "INSERT INTO user_account (name, email, contact_info, account_name, account_type) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, name, email, contactInfo, accountName, accountType);
    }

    public void viewUserAccounts() {
        String query = "SELECT * FROM user_account";
        String[] headers = {"ID", "Name", "Email", "Contact Info", "Account Name", "Account Type"};
        String[] columns = {"id", "name", "email", "contact_info", "account_name", "account_type"};
        conf.viewRecords(query, headers, columns);
    }

    private void editUserAccount() {
        System.out.print("Enter User ID to edit: ");
        int id = scan.nextInt();
        scan.nextLine();

        if (!conf.doesIDExist("user_account", id)) {
            System.out.println("User ID not found.");
            return;
        }

        System.out.println("Enter New User Account Details:");
        System.out.print("\nNew Name: ");
        String name = scan.nextLine();
        System.out.print("New Email: ");
        String email = scan.nextLine();
        System.out.print("New Contact Info: ");
        String contactInfo = scan.nextLine();
        System.out.print("New Account Name: ");
        String accountName = scan.nextLine();
        System.out.print("New Account Type: ");
        String accountType = scan.nextLine();

        String sql = "UPDATE user_account SET name = ?, email = ?, contact_info = ?, account_name = ?, account_type = ? WHERE id = ?";
        conf.updateRecord(sql, name, email, contactInfo, accountName, accountType, id);
    }

    private void deleteUserAccount() {
        System.out.print("Enter User ID to delete: ");
        int id = scan.nextInt();

        String sql = "DELETE FROM user_account WHERE id = ?";
        conf.deleteRecord(sql, id);
    }
}


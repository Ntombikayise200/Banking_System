import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main Application Class
public class BankingApp {

    // Inner Customer Class
    static class Customer {
        private int customerId;
        private String name;

        // Constructor
        public Customer(int customerId, String name) {
            this.customerId = customerId;
            this.name = name;
        }

        // Getters
        public int getCustomerId() {
            return customerId;
        }

        public String getName() {
            return name;
        }

        // toString Method
        @Override
        public String toString() {
            return "Customer ID: " + customerId + ", Name: " + name;
        }
    }

    // Inner BankAccount Class
    static class BankAccount {
        private int accountId;
        private Customer customer;
        private double balance;

        // Constructor
        public BankAccount(int accountId, Customer customer, double initialDeposit) {
            this.accountId = accountId;
            this.customer = customer;
            this.balance = initialDeposit;
        }

        // Deposit Method
        public void deposit(double amount) {
            if (amount <= 0) {
                System.out.println("Deposit amount must be positive.");
                return;
            }
            balance += amount;
            System.out.println("Deposited $" + amount + " successfully.");
        }

        // Withdraw Method
        public void withdraw(double amount) {
            if (amount <= 0) {
                System.out.println("Withdrawal amount must be positive.");
                return;
            }
            if (amount > balance) {
                System.out.println("Insufficient balance. Withdrawal failed.");
                return;
            }
            balance -= amount;
            System.out.println("Withdrew $" + amount + " successfully.");
        }

        // Get Balance
        public double getBalance() {
            return balance;
        }

        // Getters
        public int getAccountId() {
            return accountId;
        }

        public Customer getCustomer() {
            return customer;
        }

        // toString Method
        @Override
        public String toString() {
            return "Account ID: " + accountId +
                   ", Customer: [" + customer.toString() + "]" +
                   ", Balance: $" + String.format("%.2f", balance);
        }
    }

    public static void main(String[] args) {
        // Lists to store customers and accounts
        List<Customer> customers = new ArrayList<>();
        List<BankAccount> accounts = new ArrayList<>();

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to the Simple Banking Application ===");

        boolean exit = false;

        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice (1-5): ");
            String choiceInput = scanner.nextLine();
            int choice;

            // Validate menu choice input
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number between 1 and 5.");
                continue;
            }

            switch (choice) {
                case 1:
                    createNewAccount(scanner, customers, accounts);
                    break;
                case 2:
                    depositMoney(scanner, accounts);
                    break;
                case 3:
                    withdrawMoney(scanner, accounts);
                    break;
                case 4:
                    checkBalance(scanner, accounts);
                    break;
                case 5:
                    System.out.println("Thank you for using the Simple Banking Application. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice! Please select a valid option (1-5).");
            }
        }

        scanner.close();
    }

    // Method to Display the Main Menu
    private static void displayMenu() {
        System.out.println("\nPlease choose an option:");
        System.out.println("1. Create a New Account");
        System.out.println("2. Deposit Money");
        System.out.println("3. Withdraw Money");
        System.out.println("4. Check Balance");
        System.out.println("5. Exit");
    }

    // Method to Create a New Account
    private static void createNewAccount(Scanner scanner, List<Customer> customers, List<BankAccount> accounts) {
        System.out.println("\n--- Create a New Account ---");

        // Generate a unique Customer ID
        int customerId = customers.size() + 1;

        // Input Customer Name
        String name;
        while (true) {
            System.out.print("Enter Customer Name: ");
            name = scanner.nextLine();
            if (!name.trim().isEmpty()) {
                break;
            } else {
                System.out.println("Customer name cannot be empty. Please enter a valid name.");
            }
        }

        // Create Customer Object
        Customer newCustomer = new Customer(customerId, name);
        customers.add(newCustomer);
        System.out.println("Customer created successfully! " + newCustomer.toString());

        // Generate a unique Account ID
        int accountId = accounts.size() + 1;

        // Input Initial Deposit
        double initialDeposit;
        while (true) {
            System.out.print("Enter Initial Deposit Amount: $");
            String depositInput = scanner.nextLine();
            try {
                initialDeposit = Double.parseDouble(depositInput);
                if (initialDeposit < 0) {
                    System.out.println("Initial deposit cannot be negative. Please enter a valid amount.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
            }
        }

        // Create BankAccount Object
        BankAccount newAccount = new BankAccount(accountId, newCustomer, initialDeposit);
        accounts.add(newAccount);
        System.out.println("Bank account created successfully! " + newAccount.toString());
    }

    // Method to Deposit Money
    private static void depositMoney(Scanner scanner, List<BankAccount> accounts) {
        System.out.println("\n--- Deposit Money ---");

        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }

        // Input Account ID
        int accountId = getValidAccountId(scanner, accounts);
        if (accountId == -1) {
            return;
        }

        // Find the account
        BankAccount account = findAccountById(accounts, accountId);
        if (account == null) {
            System.out.println("Account not found. Please check the Account ID.");
            return;
        }

        // Input Deposit Amount
        double depositAmount;
        while (true) {
            System.out.print("Enter Deposit Amount: $");
            String depositInput = scanner.nextLine();
            try {
                depositAmount = Double.parseDouble(depositInput);
                if (depositAmount <= 0) {
                    System.out.println("Deposit amount must be greater than zero.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
            }
        }

        // Perform Deposit
        account.deposit(depositAmount);
        System.out.println("Updated Account Details: " + account.toString());
    }

    // Method to Withdraw Money
    private static void withdrawMoney(Scanner scanner, List<BankAccount> accounts) {
        System.out.println("\n--- Withdraw Money ---");

        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }

        // Input Account ID
        int accountId = getValidAccountId(scanner, accounts);
        if (accountId == -1) {
            return;
        }

        // Find the account
        BankAccount account = findAccountById(accounts, accountId);
        if (account == null) {
            System.out.println("Account not found. Please check the Account ID.");
            return;
        }

        // Input Withdrawal Amount
        double withdrawalAmount;
        while (true) {
            System.out.print("Enter Withdrawal Amount: $");
            String withdrawalInput = scanner.nextLine();
            try {
                withdrawalAmount = Double.parseDouble(withdrawalInput);
                if (withdrawalAmount <= 0) {
                    System.out.println("Withdrawal amount must be greater than zero.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
            }
        }

        // Perform Withdrawal
        account.withdraw(withdrawalAmount);
        System.out.println("Updated Account Details: " + account.toString());
    }

    // Method to Check Balance
    private static void checkBalance(Scanner scanner, List<BankAccount> accounts) {
        System.out.println("\n--- Check Balance ---");

        if (accounts.isEmpty()) {
            System.out.println("No accounts found. Please create an account first.");
            return;
        }

        // Input Account ID
        int accountId = getValidAccountId(scanner, accounts);
        if (accountId == -1) {
            return;
        }

        // Find the account
        BankAccount account = findAccountById(accounts, accountId);
        if (account == null) {
            System.out.println("Account not found. Please check the Account ID.");
            return;
        }

        // Display Balance
        System.out.println("Current Balance: $" + String.format("%.2f", account.getBalance()));
    }

    // Helper Method to Find Account by ID
    private static BankAccount findAccountById(List<BankAccount> accounts, int accountId) {
        for (BankAccount acc : accounts) {
            if (acc.getAccountId() == accountId) {
                return acc;
            }
        }
        return null;
    }

    // Helper Method to Get Valid Account ID from User
    private static int getValidAccountId(Scanner scanner, List<BankAccount> accounts) {
        int accountId;
        while (true) {
            System.out.print("Enter Account ID: ");
            String idInput = scanner.nextLine();
            try {
                accountId = Integer.parseInt(idInput);
                if (accountId <= 0) {
                    System.out.println("Account ID must be a positive integer.");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a numeric value.");
            }
        }
        return accountId;
    }
}
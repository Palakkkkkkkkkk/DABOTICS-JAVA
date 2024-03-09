import java.util.Scanner;
public class Survey {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Online Survey System!");
        
        boolean isAuthenticated = authenticateUser(scanner);
        if (isAuthenticated) {
            launchSurvey(scanner);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
        scanner.close();
    }
    
    public static boolean authenticateUser(Scanner scanner) {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        if (username.equals("admin") && password.equals("password")) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void launchSurvey(Scanner scanner) {
        System.out.println("Survey launched! Thank you for participating.");
    }
}

import java.util.Scanner;

public class Register {
    private Login loginSystem;

    public Register(Login loginSystem) {
        this.loginSystem = loginSystem;
    }

    public void createUsernameAndPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new username:");
        String newUsername = scanner.nextLine();
        if (loginSystem.getUserDatabase().containsKey(newUsername)) {
            System.out.println("Το username already exists. Try another one.");
            
        }
        System.out.print("Enter new password");
        String newPassword = scanner.nextLine();
        loginSystem.getUserDatabase().put(newUsername, newPassword);
        System.out.println("Successful register!");
        scanner.close();
    }
}

import java.util.HashMap;

public class Login {
    
    private HashMap<String, String> userDatabase;

    public Login() {
        userDatabase = new HashMap<>();
        userDatabase.put("companyA", "password123");
        userDatabase.put("companyB", "password456");
        userDatabase.put("companyC", "password789");
        userDatabase.put("companyD", "password101112");
        userDatabase.put("companyE", "password131415");
    }

    public boolean authenticate(String username, String password) {
        if (userDatabase.containsKey(username)) {
            if (userDatabase.get(username).equals(password)) {
                System.out.println("Successful enter!");
                return true;
            } else {
                System.out.println("Error! Wrong password.");
                return false;
            }
        } else {
                System.out.println("The company wasn't found.");
                return false;
        }
    } 

    public HashMap<String, String> getUserDatabase() {
        return userDatabase;
    }
}

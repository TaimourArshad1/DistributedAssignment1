import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users;
    private String filePath;

    public UserManager(String filePath) {
        this.users = new ArrayList<>();
        this.filePath = filePath;
        loadUsers();
    }

    public void addUser(User user) {
        users.add(user);
        saveUsers();
    }

    public boolean checkCredentials(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class OrderServer  {
    public static void main(String[] args) {
        int serverPort = 3500;
        int maxClients = 3;

        UserManager userManager = new UserManager("users.txt");

        try (ServerSocket ss = new ServerSocket(serverPort)) {
            System.out.println("----WELCOME TO THE DELIVERY APP!!----\n");

            while (true) {
                try {
                    Socket socket = ss.accept();

                    System.out.println("A new customer!");
                    System.out.println("IP: " + socket.getInetAddress());
                    System.out.println("Port: " + socket.getPort() + "\n");
            

                    Thread clientThread = new ClientHandler(socket, userManager);
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread{
    private final Socket socket;
    private final UserManager userManager;

    public ClientHandler(Socket socket, UserManager userManager){
        this.socket = socket;
        this.userManager = userManager;
    }

    @Override
    public void run(){
        try {
        PrintWriter pr = new PrintWriter(socket.getOutputStream(), true); 
        BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        List<String> items = new ArrayList<>();
        String choice = bf.readLine();

        if (choice.equals("login")) {
            String username = bf.readLine();
            String password = bf.readLine();

            if (userManager.checkCredentials(username, password)) {
                pr.println("Login successful!");
            } else {
                pr.println("Invalid username or password.");
            }
        } else if (choice.equals("signup")) {
            String username = bf.readLine();
            String password = bf.readLine();

            userManager.addUser(new User(username, password));
            pr.println("Sign up successful!");
        }

        pr.println("Hi! Welcome to our restaurant! What would you like to order today :)");
        pr.println("(1) - Burger - $10");
        pr.println("(2) - Pizza - $20");
        pr.println("(3) - Hotdog - $10");

        double totalCost = 0.0;

        while (true) {
            String customerOrder = bf.readLine();

            if (customerOrder == null || customerOrder.equals("q")) {
                pr.println("Would you like to pay now or at the door? (Type 'pay now' or 'pay later')");
                String paymentOption = bf.readLine();

                if (paymentOption.equals("1")) {
                    String paymentInfo = bf.readLine();
                    // Handle payment information here
                    pr.println("Thank you for your payment! Your delivery will arrive soon.");
                } else if (paymentOption.equals("2")) {
                    pr.println("Thank you! Your delivery will arrive soon. Payment can be made at the door.");
                } else {
                    pr.println("Invalid payment option. Please select 1 for 'pay now' or 2 for 'pay later'.");
                }
                break;
            } else if (customerOrder.equals("1") || customerOrder.equals("2") || customerOrder.equals("3")) {
                System.out.println("Customer ordered: " + customerOrder + "\n");
                if(customerOrder.equals("1")){
                    items.add("Burger   -   $10");

                } else if(customerOrder.equals("2")){
                    items.add("Pizza   -   $20");
                } else if(customerOrder.equals("3")){
                    items.add("Hotdog   -   $10");
                }
                totalCost += getItemPrice(customerOrder);
            } else {
                System.out.println("Invalid order" + "\n");
            }
        }
        
        pr.println("Total cost of orders: $" + totalCost);
        Receipt receipt = new Receipt(items, totalCost);
        saveReceiptToFile(receipt);
        pr.println("Your receipt has been generated. Check receipt.txt for details.");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getItemPrice(String item) {
        switch (item) {
            case "1":
                return 10.0;
            case "2":
                return 20.0;
            case "3":
                return 10.0;
            default:
                return 0.0; // Return 0 for invalid items
        }
    }

    private void saveReceiptToFile(Receipt receipt) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("receipt.txt"))) {
            writer.println("Receipt:");
            for (String item : receipt.getItems()) {
                writer.println(item);
            }
            writer.println("Total Cost: $" + receipt.getTotalCost());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
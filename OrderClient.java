import java.io.*;
import java.net.*;

public class OrderClient {
    public static void main(String[] args) throws IOException{
        try (Socket socket = new Socket("localhost", 3500)) {
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true); 
            BufferedReader passInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    
            System.out.println("Enter 'login' to log in or 'signup' to sign up:");
            String choice = passInput.readLine();
            pr.println(choice);

            if (choice.equals("login")) {
                System.out.print("Username: ");
                String username = passInput.readLine();
                pr.println(username);

                System.out.print("Password: ");
                String password = passInput.readLine();
                pr.println(password);

                String response = bf.readLine();
                System.out.println(response);

                if (response.equals("Login successful!")) {
                    orderFood(passInput, pr, bf);
                }
            } else if (choice.equals("signup")) {
                System.out.print("Enter your desired username: ");
                String username = passInput.readLine();
                pr.println(username);

                System.out.print("Enter your desired password: ");
                String password = passInput.readLine();
                pr.println(password);

                String response = bf.readLine();
                System.out.println(response);

                if (response.equals("Sign up successful!")) {
                    orderFood(passInput, pr, bf);
                }
            } else {
                System.out.print("Invalid option. Try again please.");
            }
                      
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void orderFood(BufferedReader userInput, PrintWriter pr, BufferedReader bf) throws IOException {
        String str = bf.readLine();
        System.out.println(str);

        str = bf.readLine();
        System.out.println(str);

        str = bf.readLine();
        System.out.println(str);

        str = bf.readLine();
        System.out.println(str);

        while(true) {
            userInput = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Select choice by inputting number: ");
            String order = userInput.readLine();

            if(order.equals("1")){
                System.out.println("You ordered a burger!\n");
            } else if(order.equals("2")){
                System.out.println("You ordered pizza!\n");
            } else if(order.equals("3")){
                System.out.println("You ordered a hotdog!\n");
            } else if(order.equalsIgnoreCase("q")){
                pr.println(order);
                break;
            } else{
                System.out.println("Invalid Selection. Try again\n");
            }

            pr.println(order);
        }
        
        str = bf.readLine();
        System.out.println(str);

        System.out.println("1 - Pay now");
        System.out.println("2 - Pay later");

        BufferedReader paymentInput = new BufferedReader(new InputStreamReader(System.in));
        String paymentOption = paymentInput.readLine();
        pr.println(paymentOption);
        
        if (paymentOption.equals("1")) {
            System.out.print("Please enter your payment information: ");
            String paymentInfo = paymentInput.readLine();
            pr.println(paymentInfo);
        }

        if(paymentOption.equals("1")){
            str = bf.readLine();
            System.out.println(str);

            str = bf.readLine();
            System.out.println(str);
        }

        if(paymentOption.equals("2")){
            str = bf.readLine();
            System.out.println(str);

            str = bf.readLine();
            System.out.println(str);
        }

        String response = bf.readLine();
        System.out.println(response);

        if (response.equals("Your receipt has been generated. Check receipt.txt for details.")) {
        }
    }
}
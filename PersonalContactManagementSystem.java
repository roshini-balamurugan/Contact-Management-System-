import java.util.ArrayList;
import java.util.Scanner;

// Base Class (Encapsulation)
class Contact {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;

    public Contact(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }

    // Setters
    public void setName(String name){ this.name=name; }
    public void setPhone(String phone){ this.phone=phone; }
    public void setEmail(String email){ this.email=email; }
    public void setAddress(String address){ this.address=address; }

    // Polymorphic method
    public void displayContact() {
        System.out.println("ID      : " + id);
        System.out.println("Name    : " + name);
        System.out.println("Phone   : " + phone);
        System.out.println("Email   : " + email);
        System.out.println("Address : " + address);
    }
}

// Subclass: PersonalContact (Inheritance)
class PersonalContact extends Contact {
    private String birthday;

    public PersonalContact(int id, String name, String phone, String email, String address, String birthday) {
        super(id, name, phone, email, address);
        this.birthday = birthday;
    }

    @Override
    public void displayContact() {
        super.displayContact();
        System.out.println("Birthday: " + birthday);
        System.out.println("---------------------------------------------------------");
    }
}

// Main Project Class
public class PersonalContactManagementSystem {
    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);
    private static int nextId = 1;

    // Authentication method
    private static boolean authenticate() {
        System.out.println("===== LOGIN REQUIRED =====");
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        if(username.equals("admin") && password.equals("1234")) {
            System.out.println("Login Successful!\n");
            return true;
        } else {
            System.out.println("Invalid Credentials. Access Denied.");
            return false;
        }
    }

    public static void main(String[] args) {
        if(!authenticate()) {
            return; // exit if login fails
        }

        int choice;
        while(true){
            System.out.println("PERSONAL CONTACT MANAGEMENT SYSTEM==================");
            System.out.println("1. Add Contact");
            System.out.println("2. View All Contacts");
            System.out.println("3. Search Contact");
            System.out.println("4. Update Contact");
            System.out.println("5. Delete Contact");
            System.out.println("6. Exit");
            System.out.print("Enter Your Choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: addContact(); break;
                case 2: viewContact(); break;
                case 3: searchContact(); break;
                case 4: updateContact(); break;
                case 5: deleteContact(); break;
                case 6: 
                    System.out.println("---------------------Exit Program---------------------");
                    System.exit(0);
                default: System.out.println("Invalid Choice");
            }
        }
    }

    private static void addContact() {
        System.out.print("Enter name : ");
        String name = sc.nextLine();

        // Name validation without matches()
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (!( (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == ' ' )) {
                System.out.println("Name must contain only letters (no digits or special characters).");
                return;
            }
        }

        System.out.print("Enter phone  : ");
        String phone = sc.nextLine();
        if (phone.length()!=10) {
            System.out.println("Phone Number must contain exactly 10 digits.");
            return;
        }
        for(int i=0;i<phone.length();i++){
            if (!(phone.charAt(i)>='0' && phone.charAt(i)<='9')){
                System.out.println("Phone Number must contain only digits.");
                return;
            }
        }

        System.out.print("Enter Email  : ");
        String email = sc.nextLine();

        System.out.print("Enter Address : ");
        String address = sc.nextLine();

        System.out.print("Enter Birthday (dd/MM/yyyy): ");
        String birthday = sc.nextLine();

        contacts.add(new PersonalContact(nextId++, name, phone, email, address, birthday));
        System.out.println("Contact Added Successfully.");
    }

    private static void viewContact() {
        if (contacts.isEmpty()) {
            System.out.println("No Contact found.");
            return;
        }
        System.out.println("-------------Personal Contact list---------------");
        for (Contact c : contacts) {
            c.displayContact(); // Polymorphic call
        }
    }

    private static void searchContact() {
        System.out.print("Enter Id to Search: ");
        int id = sc.nextInt();
        sc.nextLine();
        boolean found = false;

        for (Contact c : contacts) {
            if (c.getId()==id) {
                System.out.println("Contact Found:");
                c.displayContact();
                found = true;
            }
        }
        if (!found) System.out.println("Contact Not Found.");
    }

    private static void updateContact() {
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Contact c : contacts) {
            if (c.getId() == id ) {
                System.out.print("Enter new name : ");
                String name=sc.nextLine();
                // Validate name again
                for (int i = 0; i < name.length(); i++) {
                    char ch = name.charAt(i);
                    if (!( (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == ' ' )) {
                        System.out.println("Name must contain only letters (no digits or special characters).");
                        return;
                    }
                }

                System.out.print("Enter New Phone  : ");
                String phone = sc.nextLine();
                if (phone.length()!=10) {
                    System.out.println("Phone Number must contain exactly 10 digits.");
                    return;
                }
                for(int i=0;i<phone.length();i++){
                    if (!(phone.charAt(i)>='0' && phone.charAt(i)<='9')){
                        System.out.println("Phone Number must contain only digits.");
                        return;
                    }
                }

                System.out.print("Enter New Email  : ");
                String email = sc.nextLine();
                System.out.print("Enter New Address : ");
                String address = sc.nextLine();

                c.setName(name);
                c.setPhone(phone);
                c.setEmail(email);
                c.setAddress(address);

                System.out.println("Contact Updated Successfully.");
                return;
            }
        }
        System.out.println("Contact not Found.");
    }

    private static void deleteContact() {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();

        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == id ) {
                contacts.remove(i);
                System.out.println("Contact Deleted Successfully.");
                return;
            }
        }
        System.out.println("Contact not Found with that ID.");
    }
}
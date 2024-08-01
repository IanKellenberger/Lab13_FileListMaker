import java.io.*;
import java.util.*;

public class FileListMaker {
    private List<String> itemList;
    private boolean needsToBeSaved;
    private String currentFileName;

    public FileListMaker() {
        this.itemList = new ArrayList<>();
        this.needsToBeSaved = false;
        this.currentFileName = null;
    }

    public static void main(String[] args) {
        FileListMaker app = new FileListMaker();
        app.run();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String command;
        do {
            displayMenu();
            command = scanner.nextLine().toUpperCase();
            switch (command) {
                case "A":
                    addItem(scanner);
                    break;
                case "D":
                    deleteItem(scanner);
                    break;
                case "I":
                    insertItem(scanner);
                    break;
                case "V":
                    viewList();
                    break;
                case "M":
                    moveItem(scanner);
                    break;
                case "O":
                    openList(scanner);
                    break;
                case "S":
                    saveList();
                    break;
                case "C":
                    clearList(scanner);
                    break;
                case "Q":
                    quit(scanner);
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        } while (!command.equals("Q"));
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("I - Insert an item into the list");
        System.out.println("V - View the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit the program");
        System.out.print("Enter your choice: ");
    }

    private void addItem(Scanner scanner) {
        System.out.print("Enter item to add: ");
        String item = scanner.nextLine();
        itemList.add(item);
        needsToBeSaved = true;
    }

    private void deleteItem(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before deleting an item? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveList();
            }
        }
        System.out.print("Enter index of item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < itemList.size()) {
            itemList.remove(index);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void insertItem(Scanner scanner) {
        System.out.print("Enter index to insert item at: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index <= itemList.size()) {
            System.out.print("Enter item to insert: ");
            String item = scanner.nextLine();
            itemList.add(index, item);
            needsToBeSaved = true;
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void viewList() {
        System.out.println("\nList:");
        for (int i = 0; i < itemList.size(); i++) {
            System.out.println(i + ": " + itemList.get(i));
        }
    }

    private void moveItem(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before moving an item? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveList();
            }
        }
        System.out.print("Enter index of item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < itemList.size()) {
            System.out.print("Enter index to move item to: ");
            int toIndex = Integer.parseInt(scanner.nextLine());
            if (toIndex >= 0 && toIndex <= itemList.size()) {
                String item = itemList.remove(fromIndex);
                itemList.add(toIndex, item);
                needsToBeSaved = true;
            } else {
                System.out.println("Invalid index.");
            }
        } else {
            System.out.println("Invalid index.");
        }
    }

    private void openList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before opening a new list? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveList();
            }
        }
        System.out.print("Enter filename to open: ");
        String filename = scanner.nextLine();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            itemList.clear();
            String line;
            while ((line = br.readLine()) != null) {
                itemList.add(line);
            }
            currentFileName = filename;
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveList() {
        if (currentFileName == null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter filename to save: ");
            currentFileName = scanner.nextLine();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(currentFileName))) {
            for (String item : itemList) {
                bw.write(item);
                bw.newLine();
            }
            needsToBeSaved = false;
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    private void clearList(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before clearing the list? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveList();
            }
        }
        itemList.clear();
        needsToBeSaved = true;
    }

    private void quit(Scanner scanner) {
        if (needsToBeSaved) {
            System.out.print("You have unsaved changes. Do you want to save them before quitting? (y/n): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("y")) {
                saveList();
            }
        }
        System.out.println("Exiting program.");
    }
}

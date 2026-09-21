import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class SmartParkingGarage {
    private static final int MAXIMUM_CAPACITY = 10;

    private static final Queue<String> waitingVehicles = new LinkedList<>();
    private static final Stack<String> parkedVehicles = new Stack<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static int totalParkedToday = 0;
    private static int totalDepartedToday = 0;

    public static void main(String[] args) {
        int menuChoice;

        do {
            displayMenu();
            menuChoice = readMenuChoice();

            switch (menuChoice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    parkVehicle();
                    break;
                case 3:
                    removeVehicle();
                    break;
                case 4:
                    viewNextWaitingVehicle();
                    break;
                case 5:
                    viewLastParkedVehicle();
                    break;
                case 6:
                    displayWaitingQueue();
                    break;
                case 7:
                    displayParkedVehicles();
                    break;
                case 8:
                    searchVehicle();
                    break;
                case 9:
                    displayStatistics();
                    break;
                case 10:
                    clearWaitingQueue();
                    break;
                case 11:
                    clearParkingGarage();
                    break;
                case 12:
                    resetSystem();
                    break;
                case 13:
                    System.out.println("Thank you for using Smart Parking Garage.");
                    break;
                default:
                    System.out.println("Invalid menu choice. Please select a number from 1 to 13.");
            }
        } while (menuChoice != 13);

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n========= Smart Parking Garage =========");
        System.out.println("1. Add Vehicle to Waiting Queue");
        System.out.println("2. Park Next Vehicle");
        System.out.println("3. Remove Parked Vehicle");
        System.out.println("4. View Next Waiting Vehicle");
        System.out.println("5. View Last Parked Vehicle");
        System.out.println("6. Display Waiting Queue");
        System.out.println("7. Display Parked Vehicles");
        System.out.println("8. Search Vehicle");
        System.out.println("9. Display Garage Statistics");
        System.out.println("10. Clear Waiting Queue");
        System.out.println("11. Clear Parking Garage");
        System.out.println("12. Reset Entire System");
        System.out.println("13. Exit");
        System.out.print("Select an option: ");
    }

    private static int readMenuChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return -1;
        }
    }

    private static void addVehicle() {
        System.out.print("Enter the vehicle license plate number: ");
        String vehicleNumber = normalizeVehicleNumber(scanner.nextLine());

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be blank.");
            return;
        }

        if (isDuplicateVehicle(vehicleNumber)) {
            System.out.println("Vehicle already exists in the system.");
            return;
        }

        waitingVehicles.offer(vehicleNumber);
        System.out.println("Vehicle " + vehicleNumber + " added to the waiting queue successfully.");
    }

    private static void parkVehicle() {
        if (waitingVehicles.isEmpty()) {
            System.out.println("No vehicles are waiting.");
            return;
        }

        if (parkedVehicles.size() >= MAXIMUM_CAPACITY) {
            System.out.println("The parking garage is full.");
            return;
        }

        String vehicleNumber = waitingVehicles.poll();
        parkedVehicles.push(vehicleNumber);
        totalParkedToday++;
        System.out.println("Vehicle parked successfully: " + vehicleNumber);
    }

    private static void removeVehicle() {
        if (parkedVehicles.isEmpty()) {
            System.out.println("The parking garage is empty.");
            return;
        }

        String departedVehicle = parkedVehicles.pop();
        totalDepartedToday++;
        System.out.println("Vehicle removed from the garage: " + departedVehicle);

        if (!waitingVehicles.isEmpty()) {
            System.out.println("A parking space is now available. Parking the next waiting vehicle...");
            parkVehicle();
        }
    }

    private static void viewNextWaitingVehicle() {
        if (waitingVehicles.isEmpty()) {
            System.out.println("No vehicles are waiting.");
        } else {
            System.out.println("Next waiting vehicle: " + waitingVehicles.peek());
        }
    }

    private static void viewLastParkedVehicle() {
        if (parkedVehicles.isEmpty()) {
            System.out.println("The parking garage is empty.");
        } else {
            System.out.println("Last parked vehicle: " + parkedVehicles.peek());
        }
    }

    private static void displayWaitingQueue() {
        if (waitingVehicles.isEmpty()) {
            System.out.println("No vehicles are waiting.");
            return;
        }

        System.out.println("\nWaiting Vehicles (first to last):");
        int position = 1;
        for (String vehicleNumber : waitingVehicles) {
            System.out.println(position + ". " + vehicleNumber);
            position++;
        }
        System.out.println("Total Waiting Vehicles: " + waitingVehicles.size());
    }

    private static void displayParkedVehicles() {
        if (parkedVehicles.isEmpty()) {
            System.out.println("The parking garage is empty.");
        } else {
            System.out.println("\nParked Vehicles (newest to oldest):");
            int position = 1;
            for (int index = parkedVehicles.size() - 1; index >= 0; index--) {
                System.out.println(position + ". " + parkedVehicles.get(index));
                position++;
            }
        }

        System.out.println("Garage Capacity : " + MAXIMUM_CAPACITY);
        System.out.println("Occupied Spaces : " + parkedVehicles.size());
        System.out.println("Available Spaces : " + getAvailableSpaces());
    }

    private static void searchVehicle() {
        if (waitingVehicles.isEmpty() && parkedVehicles.isEmpty()) {
            System.out.println("The system contains no vehicles to search.");
            return;
        }

        System.out.print("Enter the vehicle number to search: ");
        String vehicleNumber = normalizeVehicleNumber(scanner.nextLine());

        if (vehicleNumber.isEmpty()) {
            System.out.println("Vehicle number cannot be blank.");
        } else if (waitingVehicles.contains(vehicleNumber)) {
            System.out.println("Vehicle is waiting in the queue.");
        } else if (parkedVehicles.contains(vehicleNumber)) {
            System.out.println("Vehicle is parked in the garage.");
        } else {
            System.out.println("Vehicle not found.");
        }
    }

    private static void displayStatistics() {
        System.out.println("displayStatistics is not available in this development stage.");
    }

    private static void clearWaitingQueue() {
        System.out.println("clearWaitingQueue is not available in this development stage.");
    }

    private static void clearParkingGarage() {
        System.out.println("clearParkingGarage is not available in this development stage.");
    }

    private static void resetSystem() {
        System.out.println("resetSystem is not available in this development stage.");
    }

    private static boolean isDuplicateVehicle(String vehicleNumber) {
        return waitingVehicles.contains(vehicleNumber) || parkedVehicles.contains(vehicleNumber);
    }

    private static int getAvailableSpaces() {
        return MAXIMUM_CAPACITY - parkedVehicles.size();
    }

    private static String normalizeVehicleNumber(String vehicleNumber) {
        return vehicleNumber.trim().toUpperCase();
    }
}

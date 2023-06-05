import java.io.*;
import java.util.*;

public class AddressBook {
    private Map<String, String> contacts;
    private String filePath;

    public AddressBook(String filePath) {
        this.contacts = new HashMap<>();
        this.filePath = filePath;
    }

    public void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String number = parts[0].trim();
                    String name = parts[1].trim();
                    contacts.put(number, name);
                }
            }
            System.out.println("Contactos cargados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al cargar los contactos: " + e.getMessage());
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String number = entry.getKey();
                String name = entry.getValue();
                writer.write(number + "," + name);
                writer.newLine();
            }
            System.out.println("Cambios guardados correctamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar los contactos: " + e.getMessage());
        }
    }

    public void list() {
        if (contacts.isEmpty()) {
            System.out.println("No hay contactos en la agenda.");
        } else {
            System.out.println("Contactos:");
            for (Map.Entry<String, String> entry : contacts.entrySet()) {
                String number = entry.getKey();
                String name = entry.getValue();
                System.out.println(number + " : " + name);
            }
        }
    }

    public void create(String number, String name) {
        contacts.put(number, name);
        System.out.println("Contacto creado correctamente.");
    }

    public void delete(String number) {
        if (contacts.containsKey(number)) {
            String name = contacts.get(number);
            contacts.remove(number);
            System.out.println("Contacto \"" + name + "\" eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún contacto con el número: " + number);
        }
    }

    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook("contacts.csv");
        addressBook.load();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n-- Menú de la Agenda --");
            System.out.println("1. Listar contactos");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Guardar cambios");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addressBook.list();
                    break;
                case 2:
                    scanner.nextLine(); // Consume el salto de línea pendiente
                    System.out.print("Ingrese el número de contacto: ");
                    String number = scanner.nextLine();
                    System.out.print("Ingrese el nombre de contacto: ");
                    String name = scanner.nextLine();
                    addressBook.create(number, name);
                    break;
                case 3:
                    scanner.nextLine(); // Consume el salto de línea pendiente
                    System.out.print("Ingrese el número de contacto a eliminar: ");
                    String deleteNumber = scanner.nextLine();
                    addressBook.delete(deleteNumber);
                    break;
                case 4:
                    addressBook.save();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
                    break;
            }
        } while (choice != 5);
    }
}

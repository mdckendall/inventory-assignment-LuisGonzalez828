import java.util.Scanner;
import java.util.Arrays;

// Professor I did not have enough time to add the serial number

class Inventory {
  static Product[] products = new Product[5];

  static int lastIndex = -1;

  public static void main(String[] args) {
    Inventory.mainMenu();
  }

  static void mainMenu() {

    int option = 0;
    Scanner scanner = new Scanner(System.in).useDelimiter("\n");

    while (true) {

      System.out.println("Press 1 to add an item: ");
      System.out.println("Press 2 to delete an item: ");
      System.out.println("Press 3 to update an item: ");
      System.out.println("Press 4 to show all item: ");
      System.out.println("Press 5 to quit ");

      option = scanner.nextInt();

      if (option == 1) {
        Inventory.add(scanner);
        continue;
      } else if (option == 2) {
        Inventory.remove(scanner);
        continue;
      } else if (option == 3) {
        Inventory.update(scanner);
        continue;
      } else if (option == 4) {
        Inventory.showAll();
        continue;
      } else if (option == 5) {
        System.out.print("Quiting ...");
        break;
      } else {
        System.out.print("Invalid option");
        continue;
      }
    }

    scanner.close();
  }

  static void showAll() {
    Inventory.flush();
    System.out.println("Showing all items ...");
    for (int i = 0; i < Inventory.products.length; i++) {
      Product item = Inventory.products[i];
      if (item == null)
        break;
      System.out.println(String.format(
          "%s, %s, %s",
          item.id,
          item.name,
          item.price));
    }
  }

  static void add(Scanner scanner) {
    Inventory.flush();
    System.out.println("Creating new product ...");
    int index = Inventory.lastIndex + 1;
    int id = index > 0 ? Inventory.products[Inventory.lastIndex].id + 1 : 1;

    if (index == Inventory.products.length) {
      Inventory.products = Inventory.resize(Inventory.products);
    }

    System.out.print("Enter name: ");
    String name = scanner.next();

    System.out.print("Enter price: ");
    int price = scanner.nextInt();

    Product newProduct = new Product(id, name, price);
    Inventory.products[index] = newProduct;
    System.out.println(String.format(
        "%s, %s, %s",
        newProduct.id,
        newProduct.name,
        newProduct.price));
    Inventory.lastIndex = index;
  }

  static void remove(Scanner scanner) {
    Inventory.flush();
    System.out.println("Removing an item ...");
    System.out.print("Enter id: ");
    int id = scanner.nextInt();
    Inventory.products = Arrays.stream(Inventory.products)
        .filter(item -> item != null && item.id != id)
        .toArray(Product[]::new);
    Inventory.lastIndex = Inventory.products.length - 1;
  }

  static void update(Scanner scanner) {
    Inventory.flush();
    System.out.println("Updating an item ...");
    System.out.print("Enter id: ");
    int id = scanner.nextInt();
    Product update = null;
    for (Product item : Inventory.products) {
      if (item != null && item.id == id) {
        update = item;
        break;
      }
    }
    if (update != null) {
      System.out.print("Enter name: ");
      update.name = scanner.next();
      System.out.print("Enter price: ");
      update.price = scanner.nextInt();
    } else {
      System.out.println("Item not found!");
    }
  }

  static void flush() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  static Product[] resize(Product[] old) {
    int oldSize = java.lang.reflect.Array.getLength(old);
    int newSize = oldSize + 5;
    Product[] newArray = new Product[newSize];
    int preserveLength = Math.min(oldSize, newSize);
    if (preserveLength > 0)
      System.arraycopy(old, 0, newArray, 0, preserveLength);
    return newArray;
  }
}
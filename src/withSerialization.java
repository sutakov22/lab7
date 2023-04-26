import java.io.*;
import java.util.Scanner;

class Product implements Serializable {
    String name; // наменование товара
    String manufacturer; // производитель
    int count; // количество
    int price; // цена
}

public class withSerialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in,"cp1251");
        // создается файл на диске
        File f1 = new File("E:\\laba7_out1.txt");
        f1.createNewFile();
        File f2 = new File("E:\\laba7_out2.txt");
        f2.createNewFile();
        // -------------ЗАПИСЬ ОБЪЕКТА В ФАЙЛ-------------
        // Создается поток для записи объекта
        FileOutputStream fos = new FileOutputStream(f1);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        System.out.println("Введите количество товаров => ");
        int count = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < count; i++) {
            // Вводится информация об объекте (стране)
            Product product = new Product();
            System.out.println("Введите информацию о товаре №" + (i + 1) + ": ");
            System.out.print("Название продукта => ");
            product.name = sc.nextLine();
            System.out.print("Производитель продукта => ");
            product.manufacturer = sc.nextLine();
            System.out.print("Количество единиц => ");
            product.count = sc.nextInt();
            sc.nextLine();
            System.out.print("Цена => ");
            product.price = sc.nextInt();
            sc.nextLine();
            // Объект записывается в файл
            oos.writeObject(product);
        }
        // Дописывается информация и закрывается файловый поток
        oos.flush();
        oos.close();

        // -------------ЧТЕНИЕ ОБЪЕКТА ИЗ ФАЙЛА-------------
        // Создается поток для чтения объекта из файла
        FileInputStream fis = new FileInputStream(f1);
        ObjectInputStream oin = new ObjectInputStream(fis);
        fos = new FileOutputStream(f2);
        oos = new ObjectOutputStream(fos);
        try {
            while (true) {
                // Объект считывается
                Product product = (Product) oin.readObject();
                PrintStream ps = new PrintStream(System.out, true, "cp1251");
                ps.println(product.name + " " + product.manufacturer + " " + product.count + " " + product.price); // Выводим на экран на всякий случай
                if (product.price > 1000) {
                    oos.writeObject(product); // Записываем во второй файл, если он дорогой
                }
            }
        }
        catch (EOFException e) {
            // файл закончился
        }
        // Поток закрывается
        oin.close();
        oos.flush();
        oos.close();
    }
}

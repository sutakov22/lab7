import java.io.*;
import java.util.IllegalFormatException;
import java.util.Scanner;

public class withRandomAccess {
    private static final int MAX_STRING_LENGTH = 20;

    private static String ReadString(Scanner sc) throws IllegalArgumentException {
        String out = sc.nextLine();
        if (out.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        return out;
    }

    private static String ReadFromFileString(RandomAccessFile rf) throws IOException {
        String str = rf.readUTF();
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.readByte();
        return str;
    }
    private static void WriteToFileString(RandomAccessFile rf, String str) throws IOException, IllegalArgumentException {
        if (str.length() > MAX_STRING_LENGTH)
            throw new IllegalArgumentException();
        rf.seek(rf.length()); // поиск конца файла
        rf.writeUTF(str);
        for (int i = 0; i < MAX_STRING_LENGTH - str.length(); i++)
            rf.writeByte(0);
    }

    public static void main(String[] args) throws IOException {
        File file1 = new File("E:\\laba7_randomAccess_out1.txt");
        if (!file1.createNewFile()) {
            file1.delete();
            file1.createNewFile();
        }
        RandomAccessFile raf1 = new RandomAccessFile(file1, "rw"); // чтение и запись
        Scanner sc = new Scanner(System.in, "cp1251");
        System.out.println("Введите количество товаров => ");
        int count = sc.nextInt();
        sc.nextLine();
        try {
            for (int i = 0; i < count; i++) {
                System.out.println("Введите информацию о товаре №" + (i + 1) + ": ");
                System.out.print("Название продукта => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Производитель продукта => ");
                WriteToFileString(raf1, ReadString(sc));
                System.out.print("Количество единиц => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
                System.out.print("Цена => ");
                raf1.writeInt(sc.nextInt());
                sc.nextLine();
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Неправильно! Ё*** волки! Широкую на широкую! (https://youtu.be/RbBpbwnmjJU)");
            raf1.close();
            return;
        }
        raf1.close();

        raf1 = new RandomAccessFile(file1, "r"); // только чтение
        File file2 = new File("E:\\laba7_randomAccess_out2.txt");
        if (!file2.createNewFile()) {
            file2.delete();
            file2.createNewFile();
        }
        RandomAccessFile raf2 = new RandomAccessFile(file2, "rw"); //  чтение и запись
        try {
            while (true) {
                String productName = ReadFromFileString(raf1);
                String productManufacturer = ReadFromFileString(raf1);
                int productCount = raf1.readInt();
                int productPrice = raf1.readInt();
                PrintStream ps = new PrintStream(System.out, true, "cp1251");
                ps.println(productName + " " + productManufacturer + " " + productCount + " " + productPrice); // Выводим на экран на всякий случай
                if (productPrice > 1000) {
                    WriteToFileString(raf2, productName);
                    WriteToFileString(raf2, productManufacturer);
                    raf2.writeInt(productCount);
                    raf2.writeInt(productPrice);
                }
            }
        } catch (EOFException e) {
            // файл закончился
        } catch (IllegalArgumentException e) {
            System.out.println("Неправильно! Ё*** волки! Широкую на широкую! (https://youtu.be/RbBpbwnmjJU)");
            raf2.close();
            raf1.close();
            return;
        }
        raf2.close();
        raf1.close();
    }
}

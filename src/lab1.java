import java.util.Scanner;

class Complex {
    private int real;
    private int img;

    public Complex() {
        this.real = 0;
        this.img = 0;
    }


    public Complex(int real, int img) {
        this.real = real;
        this.img = img;
    }

    public String toString(){
        if (img > 0) {
            return real + " + " + img + "i";
        } else if (img == 0) {
            return "" + real;
        } else {
            return real + " - " + (-img) + "i";
        }
    }
}

enum Operation {
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,
    TRANSPOSE,
    DETERMINANT

}


public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        //пользователь вводит номер операции
        System.out.println("Enter the operation:");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        Operation op = switch (choice) {
            case 1 -> Operation.ADD;
            case 2 -> Operation.SUBTRACT;
            case 3 -> Operation.MULTIPLY;
            case 4 -> Operation.DIVIDE;
            case 5 -> Operation.TRANSPOSE;
            case 6 -> Operation.DETERMINANT;
            default -> throw new IllegalArgumentException("Неверная операция");
        };


        Complex figure1 = new Complex(4, 5);
        Complex figure2 = new Complex(6, 0);
        Complex figure3 = new Complex(3, -2);

        System.out.println(figure1.toString());
        System.out.println(figure2.toString());
        System.out.println(figure3.toString());
    }
}


class Matrix {

}


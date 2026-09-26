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

class Matrix {
    private int row;
    private int column;
    private Complex[][] data;

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        this.data = new Complex[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                data[i][j] = new Complex(0, 0);
    }

    public Complex determinant(){
        if (row != column) {
            throw new IllegalArgumentException("The matrix must be SQUARE");
        }
    }

    private Complex determinantRecursive(){

    }
}


public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        //пользователь вводит номер операции с клавиатуры
        System.out.println("Choose the operation (1-6):");
        System.out.println("1 - ADD; 2 - SUBTRACT; 3 - MULTIPLY; 4 - DIVIDE; 5 - TRANSPOSE; 6 - DETERMINANT");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        //в зависимости от выбора операции, будет выполнен тот или иной кейс
        switch (choice) {
            case 1:
                System.out.println("1op");
                break;
            case 6:
                System.out.println("You have chosen a DETERMINANT");
            default:
                throw new IllegalArgumentException("Wrong operation");
        }



        Complex figure1 = new Complex(4, 5);
        Complex figure2 = new Complex(6, 0);
        Complex figure3 = new Complex(3, -2);

        System.out.println(figure1.toString());
        System.out.println(figure2.toString());
        System.out.println(figure3.toString());
    }
}





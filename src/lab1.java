import java.util.Scanner;

class Complex {
    private int real;
    private int img;

    //геттеры
    public int get_real() {return real;}
    public int get_img() {return img;}

    public Complex() {
        this.real = 0;
        this.img = 0;
    }

    public Complex(int real, int img) {
        this.real = real;
        this.img = img;
    }

    public String to_string(){
        if (real == 0 && img == 0){ return "0";}        //complex(0, 0)
        if (img == 0) { return String.valueOf(real);}   //complex(*, 0)
        if (real == 0) {
            if (img == 1) {return "i";}                 //complex(0, i)
            if (img == -1) {return "-i";}               //complex(0, -i)
            return img + "i";
        }
        if (img > 0) {return real + "+" + img + "i";}
        else {return real + "-" + (-img) + "i";}


    }

    //метод сложения
    public Complex add(Complex other){
        return new Complex(this.real + other.real, this.img + other.img);
    }

    //метод вычитания
    public Complex subtract(Complex other){
        return new Complex(this.real - other.real, this.img - other.img);
    }

    //метод умножение
    public Complex multiply(Complex other){
        int new_real = this.real*other.real - this.img*other.img;
        int new_img = this.real*other.img + this.img*other.real;
        return new Complex(new_real, new_img);
    }

    //метод -вайб
    public Complex negative(Complex other){
        return new Complex(-real, -img);
    }

    //метод деление
    public
}

enum Operation {
    ADD,        //+
    SUBTRACT,   //-
    MULTIPLY,   //*
    DIVIDE,     //делить
    TRANSPOSE,  //транспонировать
    DETERMINANT //детерминант

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





}


public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        //пользователь вводит номер операции с клавиатуры
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("Choose the operation (1-6):");
            System.out.println("1 - ADD; 2 - SUBTRACT; 3 - MULTIPLY; 4 - DIVIDE; 5 - TRANSPOSE; 6 - DETERMINANT");

            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: enter an integer :(\n");
            }
        }


        //в зависимости от выбора операции, будет выполнен тот или иной кейс
        switch (choice) {
            case 1:
                //сложение
                System.out.println("1op");
                break;
            case 6:
                System.out.println("You have chosen a DETERMINANT");
                break;
            default:
                //пользователь любит нарушать установленные правила, выкидываем исключение
                throw new IllegalArgumentException("Wrong operation");
        }



        Complex figure1 = new Complex(0, 5);
        Complex figure2 = new Complex(6, 0);
        Complex figure3 = new Complex(3, -2);
        Complex figure4 = new Complex(0, 0);
        Complex figure5 = new Complex(-9, -67);

        System.out.println(figure1.to_string());
        System.out.println(figure2.to_string());
        System.out.println(figure3.to_string());
        System.out.println(figure4.to_string());
        System.out.println(figure5.to_string());
    }
}





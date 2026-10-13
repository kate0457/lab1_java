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
    public Complex divide(Complex other){
        int znam = other.real*other.real + other.img*other.img;
        if (znam == 0) { throw new ArithmeticException("dividing by 0 :(");}
        int new_real = (this.real*other.real + this.img*other.img) / znam;
        int new_img = (this.img*other.real - this.real*other.img) / znam;
        return new Complex(new_real, new_img);
    }
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

    public void set(int i, int j, Complex x) { data[i][j] = x; }    //в опред. ячейку матрицы заносим значение

    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        this.data = new Complex[row][column];
        for (int i = 0; i < row; i++)
            for (int j = 0; j < column; j++)
                data[i][j] = new Complex(0, 0);
    }

    public Complex parse(String s){
        System.out.println(s);
        return new Complex(0, 0);
    }


    private static Matrix read_matrix(Scanner scanner, String name){
        while (true){
            try{
                System.out.println("Matrix " + name + ": ");
                System.out.print("  rows:  ");
                int row = Integer.parseInt(scanner.nextLine());
                System.out.print("  columns:  ");
                int column = Integer.parseInt(scanner.nextLine());

                if (row <= 0 || column <= 0){
                    System.out.println("The dimensions should be positive.");
                    continue;
                }

                Matrix m = new Matrix(row, column);
                for (int i = 0; i < row; i++){
                    for (int j = 0; j < column; j++){
                        System.out.println("  [" + i + "][" + j + "] (correct format x+yi): ");
                        String s = scanner.nextLine();
                        try{
                            m.set(i, j, parse(s));
                            break;
                        } catch (NumberFormatException e){
                            System.out.println("Error of format. Correct format examples: 4+5i, 3, -2-i, i");
                        }
                    }
                }
                return m;
            } catch (NumberFormatException e) {
                System.out.println("Error: you need an integer. Try again.");
            }
        }
    }


    public Matrix add(Matrix other){
        if (this.row != other.row || this.column != other.column) {
            throw new IllegalArgumentException("The sizes of the matrices do not match:" + this.row + "x" + this.column + " and " + other.row + "x" + other.column);
        }

        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
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

            try {   //проверка на целое число
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
                System.out.println("You have chosen an ADDITION");
                break;
            case 2:
                //вычитание
            case 3:
                //умножение
            case 4:
                //делить
            case 5:
                //транспонировать
            case 6:
                //детерминант
                System.out.println("You have chosen a DETERMINANT");
                break;
            default:
                //пользователь любит нарушать установленные правила, выкидываем исключение
                throw new IllegalArgumentException("Wrong operation");
        }



        Complex figure1 = new Complex(1, 1);
        Complex figure2 = new Complex(1, 1);
        Complex figure3 = new Complex(3, -2);
        Complex figure4 = new Complex(0, 0);
        Complex figure5 = new Complex(-9, -67);

        System.out.println(figure1.to_string());
        System.out.println(figure2.to_string());
        System.out.println(figure3.to_string());
        System.out.println(figure4.to_string());
        System.out.println(figure5.to_string());

        System.out.println(figure1.subtract(figure2).to_string());
    }
}





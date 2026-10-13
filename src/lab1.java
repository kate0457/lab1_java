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
    INVERSE,     //найти обратную матрицу
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

    //разделяем каждое введенное пользовательское комплекное число вида x+yi на действительную и мнмую части
    public static Complex parse(String s){
        //System.out.println(s);
        s = s.replace(" ", ""); //убираем пробелы
        if (!s.contains("i")) {                   //если нет i, значит в числе только действ часть
            int real = Integer.parseInt(s);
            return new Complex(real, 0);
        }
            String without_i = s.substring(0, s.length() - 1); //если есть i, отрезаем ее

            //разделяем комплексное число на две части по знаку + или -
            int pos = -1;
            for (int i = 1; i < without_i.length(); i++) {
                if (without_i.charAt(i) == '+' || without_i.charAt(i) == '-') {
                    pos = i;
                    break;
                }
            }

            //если знака нет значит число только из мнимой части
            if (pos == -1){
                int img;
                if (without_i.equals("") || without_i.equals("+")){ img = 1;}
                else if (without_i.equals("-")){ img = -1;}
                else { img = Integer.parseInt(without_i);}
                return new Complex(0, img);
            }

            //общий случай
            int real = Integer.parseInt(without_i.substring(0, pos));

            String img_st = without_i.substring(pos);
            int img;
            if (img_st.equals("+")) {img = 1;}
            else if (img_st.equals("-")) {img =-1;}
            else {img = Integer.parseInt(img_st);}

            return new Complex(real, img);
        }



    public static Matrix read_matrix(Scanner scanner, String name){
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
                        System.out.print("  [" + i + "][" + j + "] (correct format x+yi): ");
                        String s = scanner.nextLine();
                        try{
                            m.set(i, j, parse(s));
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


    public String print(){
        String st = "";
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                st = st + (data[i][j].to_string()) + "\t";
            }
            st= st + "\n";
        }
        return st;
    }


    public Matrix add(Matrix other){
        if (this.row != other.row || this.column != other.column) {
            throw new IllegalArgumentException("The sizes of the matrices do not match: " + this.row + "x" + this.column + " and " + other.row + "x" + other.column);
        }

        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                result.data[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix subtract(Matrix other){
        if (this.row != other.row || this.column != other.column) {
            throw new IllegalArgumentException("The sizes of the matrices do not match: " + this.row + "x" + this.column + " and " + other.row + "x" + other.column);
        }

        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                result.data[i][j] = this.data[i][j].subtract(other.data[i][j]);
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other){
        //проверяем, иначе умножать нельзя блин блинский
        if (this.column != other.row) {
            throw new IllegalArgumentException("The columns of first matrix (" + this.column + ") do not match the rows of second (" + other.row + ")");
        }

        //создали новую матрицу (куда запишем результат умножения)
        Matrix result = new Matrix(this.row, other.column);
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < other.column; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.column; k++){
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result.data[i][j]= sum;
            }
        }
        return result;
    }

    public Matrix transpose() {
        Matrix result = new Matrix(column, row);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.column; j++) {
                result.data[j][i] = this.data[i][j];
            }
        }
        return result;
    }

    public Complex determinant(){
        //детерминант можно искать только у квадр матриц
        if (this.row != this.column) {throw new IllegalArgumentException("The matrix must be SQUARE");}
        if (this.row == 1 && this.column == 1){ return this.data[0][0];}
        if (this.row == 2 && this.column == 2){
            return (this.data[0][0].multiply(this.data[1][1])).subtract(this.data[0][1].multiply(this.data[1][0]));
            //[0][0]*[1][1] - [0][1]*[1][0]
        }

        Complex det = new Complex(0,0);
        for (int j = 0; j < this.column; j++){
            Matrix m = this.minor(0, j);
            Complex n = this.data[0][j].multiply(m.determinant());
            if (j % 2 == 1) {
                n = n.negative(null);
            }
            det = det.add(n);
        }
        return det;
    }

    public Matrix minor(int middle_row, int middle_column) {
        Matrix result = new Matrix(this.row - 1, this.column - 1);
        int row = 0;
        for (int i = 0; i < this.row; i++){
            if (i == middle_row) {continue;}

            int column = 0;
            for (int j = 0; j < this.column; j++){
                if (j == middle_column) {continue;}
                result.data[row][column] = this.data[i][j];
                column++;
            }
            row++;
        }
        return result;
    }

    public Matrix inverse(){
        //обратные только для квадратных
        if (this.row != this.column) {
            throw new IllegalArgumentException("The matrix must be SQUARE");
        }

        Complex det = this.determinant();
        if (det.get_real() == 0 && det.get_img() == 0) {
            throw new IllegalArgumentException("The determinant is ZERO, the inverse matrix does NOT exist");
        }

        //если матрица 1*1
        if (this.row == 1 && this.column == 1){
            Matrix result = new Matrix(1,1);
            result.data[0][0] = new Complex(1, 0).divide(this.data[0][0]);
            return result;
        }

        Matrix result = new Matrix(this.row, this.column);
        for (int i = 0; i < this.row; i++){
            for (int j = 0; j < this.column; j++){
                Matrix matrix = this.minor(i, j);
                Complex minor_det = matrix.determinant();

                if ((i+j) % 2 == 1){
                    minor_det = minor_det.negative(null);
                }

                result.data[i][j] = minor_det.divide(det);
            }
        }
        return result.transpose();
    }
}



public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        String RED    = "\u001B[31m";
        String ORANGE = "\u001B[38;5;208m";
        String RESET = "\u001B[0m";

        //пользователь вводит номер операции с клавиатуры
        Scanner scanner = new Scanner(System.in);

        Operation choice = null;
        while (choice == null) {
            System.out.println("Choose the operation (1-6):");
            System.out.println("1 - ADD; 2 - SUBTRACT; 3 - MULTIPLY; 4 - TRANSPOSE; 5 - DETERMINANT; 6 - INVERSE");
            String input = scanner.nextLine();

            try {
                int number = Integer.parseInt(input);
                choice = switch(number){
                    case 1 -> Operation.ADD;
                    case 2 -> Operation.SUBTRACT;
                    case 3 -> Operation.MULTIPLY;
                    case 4 -> Operation.TRANSPOSE;
                    case 5 -> Operation.DETERMINANT;
                    case 6 -> Operation.INVERSE;
                    default -> null;
                };
                if (choice == null) { System.out.println("Error: choose 1-6");}
            } catch (NumberFormatException e) {
                System.out.println("Error: enter an integer :(\n");
            }
        }


        //в зависимости от выбора операции, будет выполнен тот или иной кейс
        switch (choice) {
            case ADD:{
                //сложение
                System.out.println("You have chosen an " + ORANGE + "ADDITION" + RESET);
                System.out.println("The " + RED + "sizes" + RESET + " of the matrices should be " + RED + "equal" + RESET);
                Matrix a = Matrix.read_matrix(scanner, "1");
                Matrix b = Matrix.read_matrix(scanner, "2");
                try{
                    Matrix result = a.add(b);
                    System.out.println("\nMatrix 1:");
                    System.out.println(a.print());

                    System.out.println("\nMatrix 2:");
                    System.out.println(b.print());

                    System.out.println("\nResult of addition:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e){
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;

            case SUBTRACT:{
                //вычитание
                System.out.println("You have chosen an " + ORANGE + "SUBTRACTION" + RESET);
                Matrix a = Matrix.read_matrix(scanner, "1");
                Matrix b = Matrix.read_matrix(scanner, "2");
                try {
                    Matrix result = a.subtract(b);
                    System.out.println("\nMatrix 1:");
                    System.out.println(a.print());

                    System.out.println("\nMatrix 2:");
                    System.out.println(b.print());

                    System.out.println("\nResult of substraction:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;

            case MULTIPLY: {
                //умножение
                System.out.println("You have chosen an " + ORANGE + "MULTIPLICATION" + RESET);
                System.out.println("The columns of the first matrix" + RED + " MUST" + RESET + " match the rows of the second matrix, otherwise the operation will " + RED + "NOT" + RESET + " be performed");
                System.out.println("[a][b] * [b][c] = [a][c]");

                Matrix a = Matrix.read_matrix(scanner, "1");
                Matrix b = Matrix.read_matrix(scanner, "2");
                try {
                    Matrix result = a.multiply(b);
                    System.out.println("\nMatrix 1:");
                    System.out.println(a.print());

                    System.out.println("\nMatrix 2:");
                    System.out.println(b.print());

                    System.out.println("\nResult of multiplication:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;

            case TRANSPOSE: {
                //транспонировать
                System.out.println("You have chosen an " + ORANGE + "TRANSPOSED MATRIX" + RESET);
                Matrix a = Matrix.read_matrix(scanner, "");
                try {
                    Matrix result = a.transpose();
                    System.out.println("\nMatrix:");
                    System.out.println(a.print());

                    System.out.println("\nTransposed matrix:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;

            case DETERMINANT: {
                //детерминант
                System.out.println("You have chosen a " + ORANGE + "DETERMINANT" + RESET);
                System.out.println("The matrix MUST be SQUARE");
                Matrix a = Matrix.read_matrix(scanner, "");
                try {
                    Complex result = a.determinant();
                    System.out.println("\nMatrix:");
                    System.out.println(a.print());

                    System.out.println("\nDeterminant:");
                    System.out.println(result.to_string());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;
            case INVERSE: {
                //найти обратную матрицу
                System.out.println("You have chosen an " + ORANGE + "INVERSED MATRIX" + RESET);
                System.out.println("The matrix " + RED + "MUST" + RESET + " be" + RED + " SQUARE" + RESET + ", and its determinant must " + RED + "NOT" + RESET + " be " + RED + "ZERO" + RESET);

                Matrix a = Matrix.read_matrix(scanner, "");
                try {
                    Matrix result = a.inverse();
                    System.out.println("\nMatrix:");
                    System.out.println(a.print());

                    System.out.println("Inversed matrix:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
                break;


            default:
                //пользователь любит нарушать установленные правила, выкидываем исключение
                throw new IllegalArgumentException("Wrong operation");
        }





        //System.out.println(figure1.to_string());
        //System.out.println(figure2.to_string());
        //System.out.println(figure3.to_string());
        //System.out.println(figure4.to_string());
        //System.out.println(figure5.to_string());

        //System.out.println(figure1.subtract(figure2).to_string());
    }
}





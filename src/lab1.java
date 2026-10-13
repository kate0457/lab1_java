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

    public determinant()
}



public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");

        //пользователь вводит номер операции с клавиатуры
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (true) {
            System.out.println("Choose the operation (1-6):");
            System.out.println("1 - ADD; 2 - SUBTRACT; 3 - MULTIPLY; 4 - TRANSPOSE; 5 - DETERMINANT; 6 - INVERSE");

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
            case 1:{
                //сложение
                System.out.println("You have chosen an ADDITION");
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

            case 2:{
                //вычитание
                System.out.println("You have chosen an SUBTRACTION");
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

            case 3: {
                //умножение
                System.out.println("You have chosen an MULTIPLICATION");
                System.out.println("The columns of the first matrix MUST match the rows of the second matrix, otherwise the operation will NOT be performed");
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

            case 4: {
                //транспонировать
                System.out.println("You have chosen an TRANSPOSED MATRIX");
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
            case 5:
                //детерминант
                System.out.println("You have chosen a DETERMINANT");
                Matrix a = Matrix.read_matrix(scanner, "");
                try {
                    Matrix result = a.determinant();
                    System.out.println("\nMatrix:");
                    System.out.println(a.print());

                    System.out.println("\nTransposed matrix:");
                    System.out.println(result.print());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
        }
        break;
            case 6:
                //найти обратную матрицу
                System.out.println("You have chosen an INVERSED MATRIX");

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





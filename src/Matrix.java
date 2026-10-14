import java.util.Scanner;

public class Matrix {
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
            double real = Double.parseDouble(s);
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
            double img;
            if (without_i.equals("") || without_i.equals("+")){ img = 1;}
            else if (without_i.equals("-")){ img = -1;}
            else { img = Double.parseDouble(without_i);}
            return new Complex(0, img);
        }

        //общий случай
        double real = Double.parseDouble(without_i.substring(0, pos));

        String img_st = without_i.substring(pos);
        double img;
        if (img_st.equals("+")) {img = 1;}
        else if (img_st.equals("-")) {img =-1;}
        else {img = Double.parseDouble(img_st);}

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
                st = st + String.format("%-8s", data[i][j].to_string()) + "\t";
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


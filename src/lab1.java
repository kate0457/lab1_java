import java.util.Scanner;

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
            System.out.println("Choose the operation (1-7):");
            System.out.println("1 - ADD; 2 - SUBTRACT; 3 - MULTIPLY; 4 - TRANSPOSE; 5 - DETERMINANT; 6 - INVERSE; 7 - DIVIDE");
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
                    case 7 -> Operation.DIVIDE;
                    default -> null;
                };
                if (choice == null) { System.out.println("Error: choose 1-7");}
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
                System.out.println("The " + RED + "sizes" + RESET + " of the matrices should be " + RED + "equal" + RESET);
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
                System.out.println("The matrix " + RED + "MUST" + RESET + " be" + RED + " SQUARE" + RESET);
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

            case DIVIDE: {
                //деление матриц
                System.out.println("You have chosen an " + ORANGE + "DIVISION" + RESET);
                System.out.println("A / B = A × B⁻¹");
                System.out.println("The SECOND matrix " + RED + "MUST" + RESET + " be" + RED + " SQUARE" + RESET + ", and its determinant must " + RED + "NOT" + RESET + " be " + RED + "ZERO" + RESET);
                System.out.println("The columns of the first matrix" + RED + " MUST" + RESET + " match the rows of the second matrix, otherwise the operation will " + RED + "NOT" + RESET + " be performed");

                Matrix a = Matrix.read_matrix(scanner, "1");
                Matrix b = Matrix.read_matrix(scanner, "2");
                try {
                    Matrix result = a.divide(b);
                    System.out.println("\nMatrix 1:");
                    System.out.println(a.print());

                    System.out.println("\nMatrix 2:");
                    System.out.println(b.print());

                    System.out.println("\nResult (A / B = A × B⁻¹):");
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





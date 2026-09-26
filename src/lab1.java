class Complex {
    private int real;
    private int img;

    public Complex() {
        this.real = 0;
        this.img = 0;
    }

    public Complex(int real) {
        this.real = real;
        this.img = 0;
    }

    public Complex(int real, int img) {
        this.real = real;
        this.img = img;
    }

    public String toString(){
        if (img >= 0) {
            return real + " + " + img + "i";
        } else {
            return real + " - " + (-img) + "i";
        }
    }
}


public class lab1 {
    public static void main(String[] args) {
        System.out.println("Hello");
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


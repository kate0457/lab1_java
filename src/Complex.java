public class Complex {
    private double real;
    private double img;

    //геттеры
    public double get_real() {return real;}
    public double get_img() {return img;}

    public Complex() {
        this.real = 0;
        this.img = 0;
    }

    public Complex(double real, double img) {
        this.real = real;
        this.img = img;
    }

    public String to_string(){
        if (real == 0 && img == 0){ return "0";}        //complex(0, 0)

        if (img == 0) {                                 //complex(*, 0)
            if (real == Math.floor(real)){
                return String.valueOf((long)real);
            }
            return String.format("%.2f", real);
        }

        if (real == 0) {
            if (img == 1) {return "i";}                 //complex(0, i)
            if (img == -1) {return "-i";}               //complex(0, -i)

            if (img == Math.floor(img)){
                return ((long)img) + "i";
            }
            return String.format("%.2f", img) + "i";
        }

        String real_str;
        if (real == Math.floor(real)){
            real_str = String.valueOf((long)real);
        } else {
            real_str = String.format("%.2f", real);
        }

        String img_str;
        double  abs = (img > 0) ? img: -img;
        if (abs == Math.floor(abs)) {
            img_str = String.valueOf((long) abs);
        } else {
            img_str = String.format("%.2f", abs);
        }

        if (img > 0) {return real_str + "+" + img_str + "i";}
        else {return real_str + "-" + (img_str) + "i";}


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
        double new_real = this.real*other.real - this.img*other.img;
        double new_img = this.real*other.img + this.img*other.real;
        return new Complex(new_real, new_img);
    }

    //метод -вайб
    public Complex negative(Complex other){
        return new Complex(-real, -img);
    }

    //метод деление
    public Complex divide(Complex other){
        double znam = other.real*other.real + other.img*other.img;
        if (znam == 0) { throw new ArithmeticException("dividing by 0 :(");}
        double new_real = (this.real*other.real + this.img*other.img) / znam;
        double new_img = (this.img*other.real - this.real*other.img) / znam;
        return new Complex(new_real, new_img);
    }
}
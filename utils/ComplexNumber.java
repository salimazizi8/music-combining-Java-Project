package ca.ubc.ece.cpen221.mp1.utils;

/**
 * This class represent the complex numbers and has certain operations
 * which are: creating new Complex Numbers, adding complex numbers, and returning
 * the magnitude of a complex number.
 */
public class ComplexNumber {
    private double real;
    private double imaginary;

    /**
     *
     * @param real the real component of a Complex Number.
     * @param imaginary the imaginary component of a Complex Number.
     */
    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    /**
     *
     * @param other the ComplexNumber we want to add into the current ComplexNumber.
     */
    public void add_complexes(ComplexNumber other) {
        this.real = this.real + other.real;
        this.imaginary = this.imaginary + other.imaginary;
    }

    /**
     *
     * @param a the ComplexNumber whose magnitude is computed.
     * @return a value which is the square root of the real and imaginary components
     * of the ComplexNumber.
     */
    public static double magnitude (ComplexNumber a) {
        double gama =  Math.sqrt(a.real * a.real + a.imaginary * a.imaginary);
        return gama;
    }
}

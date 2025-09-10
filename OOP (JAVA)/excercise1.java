import java.util.Scanner;

public class excercise1 {
    public static void main(String[] args) {
        // a) Declare two integer variables
        int num1, num2;

        // b) Declare four integer variables for results
        int sum, difference, product, quotient;

        // c) Input process
        Scanner input = new Scanner(System.in);
        System.out.print("Enter first integer: ");
        num1 = input.nextInt();
        System.out.print("Enter second integer: ");
        num2 = input.nextInt();

        // d) Compute the sum
        sum = num1 + num2;

        // e) Compute the difference (first - second)
        difference = num1 - num2;

        // f) Compute the product
        product = num1 * num2;

        // g) Compute the quotient (check for division by zero)
        if (num2 != 0) {
            quotient = num1 / num2;
        } else {
            quotient = 0; // default value if dividing by zero
            System.out.println("Warning: Division by zero is not allowed. Quotient set to 0.");
        }

        // h) Output the results
        System.out.println("Sum: " + sum);
        System.out.println("Difference: " + difference);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);

        input.close();
    }
}

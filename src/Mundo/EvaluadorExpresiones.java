package Mundo;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



public class EvaluadorExpresiones {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Evaluando");

		Scanner scan = new Scanner(new File("ejemploEvaluacion.txt"));

		ArrayList<String> listaExpresiones = new ArrayList<String>();

		String atomos = "";
		int i = 0;

		while (scan.hasNext()) {
			String linea = scan.nextLine();
			if (linea.charAt(0) == '(')
				listaExpresiones.add(linea);
			else // Print atoms
			{
				String[] c = linea.split(" ");
				if (i % 8 == 0)
					atomos = atomos + "\n" + c[0] + " = " + c[1];
				else
					atomos = atomos + "\t" + c[0] + " = " + c[1];
			}

			i++;
		}

		System.out.println(atomos);

		int num = 1;
		Scanner read = new Scanner(System.in);

		for (String unaExpresionCadena : listaExpresiones) { // Evalua la expresion
			System.out.println("Continuar? Y o N");
			if (read.next().equalsIgnoreCase("salir"))
				System.exit(0);

			ParseError posibleError = new ParseError(unaExpresionCadena);
			if (posibleError.isError())
				System.out.println("\t\tError: \n" + "\t\t" + unaExpresionCadena + " " + posibleError.errorString);
			else {
				Expresion x = new Expresion(unaExpresionCadena);
				System.out.println(num + ".\t" + unaExpresionCadena + ": " + x.evaluar());
				num++;
			}
		}
	}
}

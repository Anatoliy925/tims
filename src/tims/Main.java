package tims;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<Integer> data = new ArrayList<>();
        Scanner fileIn = new Scanner(new File("src/tims/DATA.txt"));
        Scanner fileInt = new Scanner(new File("src/tims/INTDATA.txt"));

        for (int i = 0; i < 51; i++) {
            data.add(Integer.parseInt(fileIn.nextLine()));
        }

        Integer max = Collections.max(data);
        Integer min = Collections.min(data);
        OptionalDouble average = data
                .stream()
                .mapToDouble(a -> a)
                .average();

        System.out.println("Розмах: " + (max-min));
        Collections.sort(data);
        System.out.println("Медіана: " + data.get(25));
        System.out.println("Мода: " + Mode.mode2(data));
        System.out.println("Середнє значення: " + Math.round(average.getAsDouble()));
        System.out.println("Статистичний ряд:");
        Set<Integer> distinct = new HashSet<>(data);
        double relIn;
        for (Integer s: distinct) {
            relIn = Math.round((Collections.frequency(data, s)/51.)*100);
            System.out.println(s + ": " + Collections.frequency(data, s) + " "+ relIn + "%");
        }

        System.out.println("Інтервальний статистичний ряд: ");
        for (int i = 0; i < 15; i++) {
            System.out.println(fileInt.nextLine());
        }

        Scanner fileInt1 = new Scanner(new File("src/tims/INTDATA.txt"));
        ArrayList<Integer> dataInt = new ArrayList<>();
        fileInt1.nextLine();
        for (int i = 0; i < 28; i++) {
            dataInt.add(i, fileInt1.nextInt());
        }
        long sumOfMult = 0;
        for (int i = 0; i < 27; i++){
            sumOfMult += (long) dataInt.get(i) *dataInt.get(i+1);
            i++;
        }
        double averageInt = sumOfMult/51.;
        System.out.println("Середнє значення інтервального ряду: " + averageInt);
        sumOfMult = 0;
        for (int i = 0; i < 27; i++){
            sumOfMult += (long) dataInt.get(i)*dataInt.get(i)*dataInt.get(i+1);
            i++;
        }
        double dispersion = sumOfMult/51. - averageInt*averageInt;
        double avgSqrtDeviation = Math.sqrt(dispersion);
        double cov = avgSqrtDeviation/averageInt;

        System.out.println("Дисперсія: " + dispersion);
        System.out.println("Cередньоквадратичне відхилення: " + avgSqrtDeviation);
        System.out.println("Коефіцієнт варіації: " + cov);
        sumOfMult = 0;
        for (int i = 0; i < 27; i++){
            sumOfMult += (long) Math.pow(dataInt.get(i) - averageInt, 3)*dataInt.get(i+1);
            i++;
        }
        double mom3 = sumOfMult/51.;
        BigInteger mom4 = new BigInteger("64149900000000000000000");
        double assymetry = mom3/Math.pow(avgSqrtDeviation, 3);
        BigInteger bavgSqrtDeviation = new BigInteger("462163");
        BigInteger exces = new BigInteger("1");
        exces = exces.multiply(mom4).divide(bavgSqrtDeviation).
                divide(bavgSqrtDeviation).divide(bavgSqrtDeviation).divide(bavgSqrtDeviation).subtract(BigInteger.valueOf(4));

        System.out.println("Центральний емпіричний момент 3-го порядку: " + mom3);
        System.out.println("Центральний емпіричний момент 4-го порядку: " + mom4);
        System.out.println("Коефіцієнт асиметрії: " + assymetry);
        System.out.println("Ексцес: " + exces);
    }
}

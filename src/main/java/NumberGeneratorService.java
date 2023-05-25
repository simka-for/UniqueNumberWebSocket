import java.math.BigInteger;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Сервис для генерации случайных уникальных чисел BigInteger
 */
public class NumberGeneratorService {

    private final Set<BigInteger> numbers = new TreeSet<>();

    public BigInteger generateUniqueNumber() {
        Random random = new Random();
        BigInteger newNumber = new BigInteger(128, random);
        if (numbers.contains(newNumber)) {
            return generateUniqueNumber();
        } else {
            numbers.add(newNumber);
            return newNumber;
        }
    }
}

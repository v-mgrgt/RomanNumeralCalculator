import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RomanNumeralConverter implements Convertible {
    private final int maxArabicNumber = 3999;
    private final Map<Integer, Character> mapArabicRoman = new HashMap<>();
    private final Map<Character, Integer> mapRomanArabic = new HashMap<>();

    public RomanNumeralConverter() {
        fillMapArabicRoman();
        fillMapRomanArabic();
    }

    private void fillMapArabicRoman() {
        mapArabicRoman.put(1, 'I');
        mapArabicRoman.put(5, 'V');
        mapArabicRoman.put(10, 'X');
        mapArabicRoman.put(50, 'L');
        mapArabicRoman.put(100, 'C');
        mapArabicRoman.put(500, 'D');
        mapArabicRoman.put(1000, 'M');
    }

    private void fillMapRomanArabic() {
        for (Map.Entry<Integer, Character> entry : mapArabicRoman.entrySet()) {
            mapRomanArabic.put(entry.getValue(), entry.getKey());
        }
    }

    public boolean isCorrectRomanNumber(String romanNumber) {
        if (romanNumber == null || romanNumber.isBlank()) return false;

        char[] romanNumerals = romanNumber.toUpperCase().toCharArray();
        for (char character : romanNumerals) { // проверка текста на допустимые символы
            if (!mapRomanArabic.containsKey(character)) return false;
        }

        char previousCharacter = romanNumerals[0];
        int iPreviousCharacter = 1; // количество подряд идущих символов
        int kPreviousLessNext = 0; // количество случаев, когда предыдущий символ меньше следующего

        Set<Character> mapVLD = new HashSet<>(3);

        for (int i = 1; i < romanNumerals.length; i++) {
            int previousInteger = mapRomanArabic.get(previousCharacter);
            int nextInteger = mapRomanArabic.get(romanNumerals[i]);

            switch (romanNumerals[i]) {
                case 'V', 'L', 'D' -> { // V, L, D не могут повторяться
                    if (mapVLD.contains(romanNumerals[i])) return false;
                    mapVLD.add(romanNumerals[i]);
                }
                case 'I', 'X', 'C', 'M' -> {
                    if (previousCharacter == romanNumerals[i]) { // I, X, C, M могут повторяться не более 3-х раз подряд
                        if (++iPreviousCharacter > 3) return false;
                    } else {
                        if (iPreviousCharacter > 1 && previousInteger < nextInteger) return false;
                        iPreviousCharacter = 1;
                    }
                }
            }

            if (previousInteger < nextInteger) {
                if (++kPreviousLessNext > 1) return false;
                int interval = nextInteger / previousInteger;
                if (interval != 2 && interval != 5 && interval != 10) return false;
                if (iPreviousCharacter > 1) return false;
            } else {
                kPreviousLessNext = 0;
            }

            previousCharacter = romanNumerals[i];
        }

        return true;
    }

    public boolean isCorrectArabicNumber(int arabicNumber) {
        return arabicNumber > 0 && arabicNumber <= maxArabicNumber;
    }

    public int convertToArabicNumber(String romanNumber) {
        if (!isCorrectRomanNumber(romanNumber)) {
            throw new IllegalArgumentException("Ошибка в написании: " + romanNumber);
        }

        char[] romanNumerals = romanNumber.toUpperCase().toCharArray();
        int[] arabicNumerals = new int[romanNumerals.length];
        for (int i = 0; i < romanNumerals.length; i++) {
            arabicNumerals[i] = mapRomanArabic.get(romanNumerals[i]);
        }

        int result = 0;
        for (int i = 0, j = i + 1; i < arabicNumerals.length; i++, j++) {
            if (j < arabicNumerals.length && arabicNumerals[i] < arabicNumerals[j]) {
                result -= arabicNumerals[i];
            } else {
                result += arabicNumerals[i];
            }
        }

        return result;
    }

    public String convertFromArabicNumber(int arabicNumber) {
        if (!isCorrectArabicNumber(arabicNumber)) {
            throw new IllegalArgumentException("Максимально допустимое положительное число: " + maxArabicNumber);
        }

        char[] arabicNumerals = String.valueOf(arabicNumber).toCharArray();

        StringBuilder romanNumberBuilder = new StringBuilder();
        for (int i = 0; i < arabicNumerals.length; i++) {
            int k = (int) Math.pow(10, arabicNumerals.length - 1 - i);
            switch (arabicNumerals[i]) {
                case '4' -> {
                    romanNumberBuilder.append(mapArabicRoman.get(k));
                    romanNumberBuilder.append(mapArabicRoman.get(5 * k));
                }
                case '9' -> {
                    romanNumberBuilder.append(mapArabicRoman.get(k));
                    romanNumberBuilder.append(mapArabicRoman.get(10 * k));
                }
                default -> {
                    if (arabicNumerals[i] > '4') {
                        arabicNumerals[i] -= 5;
                        romanNumberBuilder.append(mapArabicRoman.get(5 * k));
                    }
                    while (arabicNumerals[i]-- > '0') {
                        romanNumberBuilder.append(mapArabicRoman.get(k));
                    }
                }
            }
        }

        return romanNumberBuilder.toString();
    }
}

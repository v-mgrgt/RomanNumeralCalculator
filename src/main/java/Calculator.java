public record Calculator(Convertible convertible) {

    public String sum(String var0, String var1) {
        return convertible.convertFromArabicNumber(convertible.convertToArabicNumber(var0)
                + convertible.convertToArabicNumber(var1));
    }
}

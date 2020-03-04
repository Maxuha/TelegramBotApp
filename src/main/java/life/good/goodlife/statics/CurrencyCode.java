package life.good.goodlife.statics;

public class CurrencyCode {
    public static String getFlagByCurrencyCode(int code) {
        String result;
        switch (code) {
            case 980: result = "\uD83C\uDDFA\uD83C\uDDE6";
                break;
            case 840: result = "\uD83C\uDDFA\uD83C\uDDF8";
                break;
            case 978: result = "\uD83C\uDDEA\uD83C\uDDFA";
                break;
            case 643: result = "\uD83C\uDDF7\uD83C\uDDFA";
                break;
            case 985: result = "\uD83C\uDDF5\uD83C\uDDF1";
                break;
            default: result = null;
        }
        return result;
    }

    public static String getCurrencyNameByCurrencyCode(int code) {
        String result;
        switch (code) {
            case 980: result = "гривня";
                break;
            case 840: result = "доллар";
                break;
            case 978: result = "эвро";
                break;
            case 643: result = "рубль";
                break;
            case 985: result = "злотий";
                break;
            default: result = null;
        }
        return result;
    }
}

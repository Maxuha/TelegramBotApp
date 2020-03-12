package life.good.goodlife.model.monobonk;

public class CurrencyCodeFactory {
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

    public static String getSymbolByCurrencyCode(int code) {
        String result;
        switch (code) {
            case 980: result = "₴";
                break;
            case 840: result = "$";
                break;
            case 978: result = "€";
                break;
            case 643: result = "₽";
                break;
            case 985: result = "zł";
                break;
            default: result = null;
        }
        return result;
    }

    public static CartCurrencyCode getCartCurrencyNameByCurrencyCode(int code) {
        CartCurrencyCode cartCurrencyCode;
        switch (code) {
            case 980: cartCurrencyCode = CartCurrencyCode.гривневая;
            break;
            case 978: cartCurrencyCode = CartCurrencyCode.эвровая;
            break;
            case 840: cartCurrencyCode = CartCurrencyCode.долларовая;
            break;
            default:cartCurrencyCode = CartCurrencyCode.none;
        }
        return cartCurrencyCode;
    }
}

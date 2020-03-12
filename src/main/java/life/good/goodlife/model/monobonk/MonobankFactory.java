package life.good.goodlife.model.monobonk;

public class MonobankFactory {
    public static CartNameType getNameTypeCartByType(String type) {
        CartNameType cartNameType;
        switch (type) {
            case "black": cartNameType = CartNameType.Чёрная;
            break;
            case "white": cartNameType = CartNameType.Белая;
            break;
            case "platinum": cartNameType = CartNameType.Платиновая;
            break;
            case "iron": cartNameType = CartNameType.Железная;
            break;
            default: cartNameType = CartNameType.none;
        }
        return cartNameType;
    }
}

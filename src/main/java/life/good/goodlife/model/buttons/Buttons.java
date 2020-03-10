package life.good.goodlife.model.buttons;

public class Buttons {
    private static String[] newsButton;
    private static String[] mainButton;

    /**
     * 0 - Next five news
     * 1 - General
     * 2 - Music
     * 3 - Entertainment
     * 4 - Health
     * 5 - Science
     * 6 - Technology
     * 7 - Sport
     * 8 - Business
     * @return button
     */
    public static String[] getNewsButton() {
        if (newsButton == null) {
            newsButton = new String[]{"Следущие 5️⃣ новостей 📰", "Главные", "Музыка \uD83C\uDFB6",
                    "Развлечение \uD83D\uDD79", "Здоровье \uD83C\uDFE5", "Наука \uD83E\uDDEC",
                    "Технологии", "Спорт \uD83C\uDFC5", "Бизнес"};
        }
        return newsButton;
    }

    public static String[] getMainButton() {
        if (mainButton == null) {
            mainButton = new String[]{"Главное меню"};
        }
        return mainButton;
    }
}

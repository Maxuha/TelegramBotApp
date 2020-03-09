package life.good.goodlife.model.news;

public class CategoryNewsFactory {
    public static CategoryNews getCategoryNews(String category) {
        CategoryNews categoryNews;
        switch (category) {
            case "Музыка": categoryNews = CategoryNews.music;
            break;
            case "Главные": categoryNews = CategoryNews.general;
            break;
            case "Здоровья": categoryNews = CategoryNews.health;
            break;
            case "Наука": categoryNews = CategoryNews.science;
            break;
            case "Спорт": categoryNews = CategoryNews.sports;
            break;
            case "Технологии": categoryNews = CategoryNews.technology;
            break;
            case "Развлечение": categoryNews = CategoryNews.entertainment;
            break;
            case "Бизнес": categoryNews = CategoryNews.business;
            break;
            default:categoryNews = CategoryNews.none;
        }
        return categoryNews;
    }
}

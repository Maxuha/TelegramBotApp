package life.good.goodlife.service.news;

import com.google.gson.Gson;
import life.good.goodlife.model.news.Article;
import life.good.goodlife.model.news.News;
import life.good.goodlife.statics.Request;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewsServiceImpl implements NewsService {
    private final String token;

    public NewsServiceImpl() {
        token = System.getenv().get("news_token");
    }

    @Override
    public String getNews(int count, int offset, String category) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key", token);
        String data = Request.get("https://newsapi.org/v2/top-headlines?country=ua&category=" + category +
                "&pageSize=" + count + "&page=" + offset, headers);
        Gson gson = new Gson();
        News news = gson.fromJson(data, News.class);
        StringBuilder result = new StringBuilder();
        Article[] articles = news.getArticles();
        for (Article article : articles) {
            result.append("[").append("Опубликовано: ").append(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm",
                    Locale.forLanguageTag("Ua")).parse(article.getPublishedAt())).append("](").append(article.getUrl()).append(")");
        }
        return result.toString();
    }
}

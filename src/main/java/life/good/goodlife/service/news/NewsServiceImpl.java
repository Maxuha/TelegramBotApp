package life.good.goodlife.service.news;

import com.google.gson.Gson;
import life.good.goodlife.model.news.Article;
import life.good.goodlife.model.news.News;
import life.good.goodlife.statics.Request;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    private final String token;

    public NewsServiceImpl() {
        token = System.getenv().get("news_token");
    }

    @Override
    public Article[] getNews(int page, String category) {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Api-Key", token);
        String data = Request.get("https://newsapi.org/v2/top-headlines?country=ua&category=" + category
                + "&page=" + page + "&pageSize=20", headers);
        Gson gson = new Gson();
        News news = gson.fromJson(data, News.class);
        return news.getArticles();
    }
}

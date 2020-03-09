package life.good.goodlife.service.news;

import life.good.goodlife.model.news.News;

public interface NewsService {
    News getNews(int page, String category);
}

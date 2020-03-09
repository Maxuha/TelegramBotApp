package life.good.goodlife.service.news;

import org.springframework.stereotype.Service;

@Service
public interface NewsService {
    String getNews(int count, int offset, String category);
}

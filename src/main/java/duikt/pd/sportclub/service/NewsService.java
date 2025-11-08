package duikt.pd.sportclub.service;

import duikt.pd.sportclub.dto.NewsDto;
import duikt.pd.sportclub.entity.News;
import java.util.List;

public interface NewsService {
    News createNews(NewsDto newsDto, String phoneNumber);

    News updateNews(Long newsId, NewsDto newsDto);

    void deleteNews(Long newsId);

    List<News> findAllSorted();

    News findById(Long newsId);

    List<News> findLatest3News();
}

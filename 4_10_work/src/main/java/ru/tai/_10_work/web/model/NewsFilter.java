package ru.tai._10_work.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.tai._10_work.model.News;

@Data
@NoArgsConstructor
public class NewsFilter {

    private Long categoryId;
    private Long userId;

}

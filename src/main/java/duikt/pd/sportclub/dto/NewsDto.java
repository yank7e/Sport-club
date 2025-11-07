package duikt.pd.sportclub.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {

    private Long id;
    private String title;
    private String content;
    private String imageUrl;

}

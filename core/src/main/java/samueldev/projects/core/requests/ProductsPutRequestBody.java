package samueldev.projects.core.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsPutRequestBody {

    private Long id;
    private String name;
    private double price;
    private int score;
    private String image;

}

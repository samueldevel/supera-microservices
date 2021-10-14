package samueldev.projects.products.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsPostRequestBody {
    private String name;

    private double price;

    private int score;

    private String image;
}

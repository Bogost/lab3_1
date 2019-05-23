package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductBuilder {

    Id productId = new Id("-1");
    Money price = new Money(0);
    String name = "default";
    ProductType type = ProductType.STANDARD;

    public ProductBuilder() {}

    public ProductBuilder withId(Id id) {
        productId = id;
        return this;
    }

    public ProductBuilder withPrice(Money money) {
        price = money;
        return this;
    }

    public ProductBuilder withName(String s) {
        name = s;
        return this;
    }

    public ProductBuilder withType(ProductType productType) {
        type = productType;
        return this;
    }

    public Product build() {
        return new Product(productId, price, name, type);
    }
}

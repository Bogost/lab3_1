package pl.com.bottega.ecommerce.sales.domain.productscatalog;

import java.util.Calendar;
import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder {

    Id productId = new Id("-1");
    Money price = new Money(0);
    String name = "default";
    ProductType type = ProductType.STANDARD;
    Date snapshotDate = defaultDate();

    private static Date defaultDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2000, 0, 1);
        return cal.getTime();
    }

    public ProductDataBuilder() {}

    public ProductDataBuilder withId(Id id) {
        productId = id;
        return this;
    }

    public ProductDataBuilder withPrice(Money money) {
        price = money;
        return this;
    }

    public ProductDataBuilder withName(String s) {
        name = s;
        return this;
    }

    public ProductDataBuilder withType(ProductType productType) {
        type = productType;
        return this;
    }

    public ProductDataBuilder withDate(Date date) {
        snapshotDate = date;
        return this;
    }

    public ProductData build() {
        return new ProductData(productId, price, name, type, snapshotDate);
    }

}

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

    public ProductData build() {
        return new ProductData(productId, price, name, type, snapshotDate);
    }

}

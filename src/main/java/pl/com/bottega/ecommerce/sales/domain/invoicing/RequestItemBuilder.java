package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductDataBuilder;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class RequestItemBuilder {

    ProductData productData = new ProductDataBuilder().build();
    int quantity = 1;
    Money totalCost = new Money(1);

    public RequestItemBuilder() {}

    public RequestItemBuilder withProductData(ProductData pd) {
        productData = pd;
        return this;
    }

    public RequestItemBuilder withQuantity(int i) {
        quantity = i;
        return this;
    }

    public RequestItemBuilder withTotalCost(Money m) {
        totalCost = m;
        return this;
    }

    public RequestItem build() {
        return new RequestItem(productData, quantity, totalCost);
    }

}

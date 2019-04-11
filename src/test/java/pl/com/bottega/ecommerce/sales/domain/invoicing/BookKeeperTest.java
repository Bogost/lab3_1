package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

    private Date date;

    private BookKeeper bookKeeper;
    private ClientData clientData;

    private TaxPolicy noTaxPolicy;

    private ProductData apple;

    private RequestItem tenApples;

    @Before
    public void setup() {
        // Date
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 5);
        date = cal.getTime();
        // ----------------------------------------------------------
        bookKeeper = new BookKeeper(new InvoiceFactory());
        clientData = new ClientData(Id.generate(), "TestClient");
        // tax policies
        noTaxPolicy = Mockito.mock(TaxPolicy.class);
        Tax taxForTaxPolicy = new Tax(new Money(0), "no tax");
        Mockito.when(noTaxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
               .thenReturn(taxForTaxPolicy);
        // products-------------------------------------------------------------------------------
        // apple = new ProductData(Id.generate(), new Money(0.5), "apple", ProductType.FOOD, date);
        apple = Mockito.mock(ProductData.class);
        Mockito.when(apple.getProductId())
               .thenReturn(new Id("0"));
        Mockito.when(apple.getPrice())
               .thenReturn(new Money(0.5));
        Mockito.when(apple.getName())
               .thenReturn("apple");
        Mockito.when(apple.getType())
               .thenReturn(ProductType.FOOD);
        Mockito.when(apple.getSnapshotDate())
               .thenReturn(date);
        // request items--------------------------------------------------------------------------
        tenApples = new RequestItem(apple, 10, new Money(5));

    }

    @Test
    public void BookKeeperAskedForInvoiceWithOnePositionShouldReturnSuchInvoice() {
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        invoiceRequest.add(tenApples);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, noTaxPolicy);

        assertThat(invoice.getItems()
                          .size(),
                Matchers.comparesEqualTo(1));
    }
}

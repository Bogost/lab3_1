package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    private ProductData peniciline;

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

        peniciline = Mockito.mock(ProductData.class);
        Mockito.when(peniciline.getProductId())
               .thenReturn(new Id("1"));
        Mockito.when(peniciline.getPrice())
               .thenReturn(new Money(3));
        Mockito.when(peniciline.getName())
               .thenReturn("peniciline");
        Mockito.when(peniciline.getType())
               .thenReturn(ProductType.DRUG);
        Mockito.when(peniciline.getSnapshotDate())
               .thenReturn(date);

    }

    @Test
    public void BookKeeperAskedForInvoiceWithOnePositionShouldReturnSuchInvoice() {
        RequestItem tenApples = new RequestItem(apple, 10, new Money(5));
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        invoiceRequest.add(tenApples);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, noTaxPolicy);

        assertThat(invoice.getItems()
                          .size(),
                Matchers.comparesEqualTo(1));
    }

    @Test
    public void BookKeeperAskedForInvoiceWithoutPositionsShouldReturnSuchInvoice() {
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, noTaxPolicy);

        assertThat(invoice.getItems()
                          .size(),
                Matchers.comparesEqualTo(0));
    }

    @Test
    public void BookKeeperAskedForInvoiceWithTwoPositionShouldCalculateTaxTwoTimes() {
        RequestItem fiveApples = new RequestItem(apple, 5, new Money(2.5));
        RequestItem onePeniciline = new RequestItem(peniciline, 1, new Money(3));

        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        invoiceRequest.add(fiveApples);
        invoiceRequest.add(onePeniciline);

        Invoice invoice = bookKeeper.issuance(invoiceRequest, noTaxPolicy);

        verify(noTaxPolicy, times(2)).calculateTax(any(ProductType.class), any(Money.class));
    }

}

package pl.com.bottega.ecommerce.sales.application.api.handler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;

public class AddProductCommandHandlerTest {

    private Product pineapple;
    private Product banana;

    private ProductRepository productRepository;

    private SuggestionService suggestionService;

    private ReservationRepository reservationRepository;

    private ClientRepository clientRepository;

    private SystemContext systemContext;

    private Reservation reservation;

    @Before
    public void setup() {
        // Reservation reservation = Mockito.mock(Reservation.class);
        reservation = Mockito.mock(Reservation.class);

        reservationRepository = Mockito.mock(ReservationRepository.class);
        Mockito.when(reservationRepository.load(any(Id.class)))
               .thenReturn(reservation);

        Client client = Mockito.mock(Client.class);

        clientRepository = Mockito.mock(ClientRepository.class);
        Mockito.when(clientRepository.load(any(Id.class)))
               .thenReturn(client);

        systemContext = new SystemContext();// zawsze zwróci usera o id 1

        // pineapple = new Product(new Id("0"), new Money(3), "Pineapple", ProductType.FOOD);
        pineapple = Mockito.mock(Product.class);
        Mockito.when(pineapple.getPrice())
               .thenReturn(new Money(3));
        Mockito.when(pineapple.getName())
               .thenReturn("pineapple");
        Mockito.when(pineapple.getProductType())
               .thenReturn(ProductType.FOOD);

        banana = Mockito.mock(Product.class);
        Mockito.when(banana.getPrice())
               .thenReturn(new Money(1));
        Mockito.when(banana.getName())
               .thenReturn("banana");
        Mockito.when(banana.getProductType())
               .thenReturn(ProductType.FOOD);

        productRepository = Mockito.mock(ProductRepository.class);
        Mockito.when(productRepository.load(new Id("0")))
               .thenReturn(pineapple);
        Mockito.when(productRepository.load(new Id("1")))
               .thenReturn(banana);

        suggestionService = Mockito.mock(SuggestionService.class);
        // can be only raw values or only matchers
        Mockito.when(suggestionService.suggestEquivalent(eq(pineapple), any(Client.class)))
               .thenReturn(banana);
    }

    @Test
    public void AddProductCommandHandlerShouldSaveReservationToItsRepesitoryOnce() {
        AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository,
                suggestionService, clientRepository, systemContext);

        Mockito.when(pineapple.isAvailable())
               .thenReturn(true);
        // second id for pineapple
        AddProductCommand addProductCommand = new AddProductCommand(new Id("dontMatter"), new Id("0"), 5);
        addProductCommandHandler.handle(addProductCommand);
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void AddProductCommandHandlerShouldAddToReservationAvailableProduct() {
        AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository,
                suggestionService, clientRepository, systemContext);
        int quantity = 5;

        Mockito.when(pineapple.isAvailable())
               .thenReturn(true);
        // second id for pineapple
        AddProductCommand addProductCommand = new AddProductCommand(new Id("dontMatter"), new Id("0"), quantity);
        addProductCommandHandler.handle(addProductCommand);

        verify(reservation, times(1)).add(pineapple, quantity);
    }

    @Test
    public void AddProductCommandHandlerShouldAddToReservationSuggestedProductIfTherIsNoRequiredProduct() {
        AddProductCommandHandler addProductCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository,
                suggestionService, clientRepository, systemContext);
        int quantity = 5;

        Mockito.when(pineapple.isAvailable())
               .thenReturn(false);
        Mockito.when(banana.isAvailable())
               .thenReturn(true);
        // second id for pineapple
        AddProductCommand addProductCommand = new AddProductCommand(new Id("dontMatter"), new Id("0"), quantity);
        addProductCommandHandler.handle(addProductCommand);

        verify(reservation, times(1)).add(banana, quantity);
    }

}

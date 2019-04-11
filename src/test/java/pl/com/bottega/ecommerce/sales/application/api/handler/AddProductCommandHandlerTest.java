package pl.com.bottega.ecommerce.sales.application.api.handler;

import static org.mockito.Matchers.any;

import org.junit.Before;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;

public class AddProductCommandHandlerTest {

    @Before
    public void setup() {
        Reservation reservation = Mockito.mock(Reservation.class);

        ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
        Mockito.when(reservationRepository.load(any(Id.class)))
               .thenReturn(reservation);
    }

    @test
    public void nn() {
        AddProductCommandHandler addProductCommandHandler =
                new addProductCommandHandler()
    }
}

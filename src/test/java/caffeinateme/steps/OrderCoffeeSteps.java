package caffeinateme.steps;

import caffeinateme.model.CoffeeShop;
import caffeinateme.model.Customer;
import caffeinateme.model.Order;
import caffeinateme.model.OrderStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;


public class OrderCoffeeSteps {

    Customer cathy = new Customer("cathy");
    CoffeeShop barista = new CoffeeShop();
    Order order;

    @Given("Cathy is {int} metres from the coffee shop")
    public void cathy_is_n_metres_from_the_coffee_shop(Integer distanceFromShop) {
        cathy.setDistanceFromShop(distanceFromShop);
    }

    @When("^Cathy (?:orders|has ordered) a (.*)$")
    public void cathy_orders_a_large_cappuccino(String orderedProduct) {
        this.order = Order.of(1, orderedProduct).forCustomer(cathy);
        cathy.placesAnOrderFor(order).at(barista);
    }

    @Then("^Barry should receive the order$")
    public void barry_should_receive_the_order() {
        assertThat(barista.getPendingOrders()).contains(order);
    }

    @Then("^Barry should know that the order is (.*)")
    public void barry_should_know_that_the_order_is(OrderStatus expectedStatus) {
        Order cathyOrder = barista.getOrderFor(cathy).orElseThrow(() ->new AssertionError("No such order"));
        assertThat(cathyOrder.getStatus()).isEqualTo(expectedStatus);
    }
}

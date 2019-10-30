import com.app.service.DataGenerator;
import com.app.service.MenuService;
import com.app.service.OrdersService;

public class App {
    public static void main(String[] args) {
      /* EmailService emailService = new EmailService();
        emailService.sendAsHtml("pawelbicki@gmail.com", "TEST MESSAGE", "<h1>First message</h1>"); */


        final String FILENAME = "orders.json";
        /*DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.generateOrders(FILENAME, 10);*/
        var ordersService = new OrdersService(FILENAME);

        MenuService menuService = new MenuService(ordersService);
        menuService.mainMenu();

    }
}

package cinema.controllers;

import cinema.model.Order;
import cinema.model.dto.request.OrderRequestDto;
import cinema.service.OrderService;
import cinema.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService,UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public String completeOrder(@RequestParam(name = "userId") Long id) {
        orderService.completeOrder(userService.findById(id));
        return "Your order is completed!";

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<OrderRequestDto> getAllUserOrders(@RequestParam(name = "userId")Long userId) {
        return orderService.getOrderHistory(userService.findById(userId))
                .stream()
                .map(this::getOrderDtoFromEntity)
                .collect(Collectors.toList());
    }

    private  OrderRequestDto getOrderDtoFromEntity(Order order) {
        OrderRequestDto orderDto = new OrderRequestDto();
        orderDto.setId(order.getId());
        orderDto.setTickets(order.getTickets());
        orderDto.setLocalDateTime(order.getLocalDateTime());
        return orderDto;
    }
}

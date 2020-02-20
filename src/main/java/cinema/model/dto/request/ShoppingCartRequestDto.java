package cinema.model.dto.request;

import cinema.model.Ticket;
import java.util.List;

public class ShoppingCartRequestDto {
    private Long id;
    List<Ticket> tickets;
    Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

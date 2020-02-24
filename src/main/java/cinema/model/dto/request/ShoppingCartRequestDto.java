package cinema.model.dto.request;

import java.util.List;

public class ShoppingCartRequestDto {
    private Long id;
    private List<TicketRequestDto> tickets;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<TicketRequestDto> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketRequestDto> tickets) {
        this.tickets = tickets;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

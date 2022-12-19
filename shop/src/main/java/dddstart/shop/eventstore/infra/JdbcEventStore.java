package dddstart.shop.eventstore.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dddstart.shop.eventstore.api.EventEntry;
import dddstart.shop.eventstore.api.EventStore;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JdbcEventStore implements EventStore {
    private ObjectMapper objectMapper;
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Object event) {
        EventEntry entry = new EventEntry(event.getClass().getName(), "application/json", toJson(event));
        jdbcTemplate.update("insert into evententry " +
                "(type, content_type, payload, timestamp) " +
                "values (?, ?, ?, ?)", ps -> {
            ps.setString(1, entry.getType());
            ps.setString(2, entry.getContentType());
            ps.setString(3, entry.getPayload());
            ps.setTimestamp(4, new Timestamp(entry.getTimestamp()));
        });
    }

    private String toJson(Object event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("이벤트 변환 문제");
        }
    }

    @Override
    public List<EventEntry> get(long offset, long limit) {
        return jdbcTemplate.query(
                "select * from evententry order by id asc limit ?, ?",
                ps -> {
                    ps.setLong(1, offset);
                    ps.setLong(2, limit);
                },
                (rs, rowNum) -> {
                    return new EventEntry(
                            rs.getLong("id"),
                            rs.getString("type"),
                            rs.getString("content_type"),
                            rs.getString("payload"),
                            rs.getTimestamp("timestamp").getTime());
                });
    }
}

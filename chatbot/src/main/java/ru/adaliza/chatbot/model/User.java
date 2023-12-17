package ru.adaliza.chatbot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ru.adaliza.chatbot.command.BotCommand;

@Getter
@Table("users")
public class User implements Persistable<Long> {
    @Id private final Long id;
    private final String username;
    @Transient private final boolean isNew;
    @Setter private BotCommand chatPhase;
    @Setter private Integer mainMessageId;

    @MappedCollection(idColumn = "id")
    private ShoppingList shoppingList;

    public User(long id, String username, BotCommand chatPhase, boolean isNew) {
        this.id = id;
        this.username = username;
        this.chatPhase = chatPhase;
        this.isNew = isNew;
    }

    @PersistenceCreator
    public User(long id, String username, BotCommand chatPhase) {
        this(id, username, chatPhase, false);
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Override
    public Long getId() {
        return id;
    }
}

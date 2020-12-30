package cz.craftmania.crafteconomy.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EconomyLog {

    @NonNull
    private String receiver;

    @NonNull
    private UUID receiverUUID;

    private String sender;

    private UUID senderUUID;

    @NonNull
    private EconomyAction action;

    @NonNull
    private Long amount;

    @NonNull
    private Long time;

    public enum EconomyAction {
        MONEY_DEPOSIT("§cVklad"),
        MONEY_WITHDRAW("§aVýběr"),
        PAY_COMMAND("Platba"); // Do not add colour here, because player can receive and send money too (manually add colours when used)

        private String translated;

        EconomyAction(String translated) {
            this.translated = translated;
        }

        public String getTranslated() {
            return translated;
        }
    }
}

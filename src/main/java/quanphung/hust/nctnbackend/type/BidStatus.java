package quanphung.hust.nctnbackend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BidStatus {

    BIDDING("Bidding"),
    WON("Won"),
    LOST("Lost");

    private final String value;
}

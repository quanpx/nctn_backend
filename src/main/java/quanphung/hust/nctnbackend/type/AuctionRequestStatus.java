package quanphung.hust.nctnbackend.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuctionRequestStatus
{
    PENDING("pending"),
    APPROVED("approved"),
    REJECT("reject");

    private final String value;
}

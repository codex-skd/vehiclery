package com.skd.vehiclery.automobile.attachment.rear;

import com.skd.vehiclery.automobile.attachment.RearAttachmentType;
import com.skd.vehiclery.entity.AutomobileEntity;

public class PassengerSeatRearAttachment extends RearAttachment {
    public PassengerSeatRearAttachment(RearAttachmentType<?> type, AutomobileEntity entity) {
        super(type, entity);
    }

    @Override
    public boolean isRideable() {
        return true;
    }

    @Override
    public double getPassengerHeightOffset() {
        return 0.69;
    }
}

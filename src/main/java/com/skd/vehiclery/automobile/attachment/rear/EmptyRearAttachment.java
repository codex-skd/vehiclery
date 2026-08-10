package com.skd.vehiclery.automobile.attachment.rear;

import com.skd.vehiclery.automobile.attachment.RearAttachmentType;
import com.skd.vehiclery.entity.AutomobileEntity;

public class EmptyRearAttachment extends RearAttachment {
    public EmptyRearAttachment(RearAttachmentType<?> type, AutomobileEntity entity) {
        super(type, entity);
    }

    @Override
    public void tick() {
    }
}

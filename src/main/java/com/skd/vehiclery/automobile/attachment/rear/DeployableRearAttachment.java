package com.skd.vehiclery.automobile.attachment.rear;

import com.skd.vehiclery.automobile.attachment.RearAttachmentType;
import com.skd.vehiclery.entity.AutomobileEntity;

public abstract class DeployableRearAttachment extends RearAttachment {

    protected DeployableRearAttachment(RearAttachmentType<?> type, AutomobileEntity entity) {
        super(type, entity);
    }

    public abstract void deploy();
}

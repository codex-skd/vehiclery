package com.skd.vehiclery.automobile.attachment.front;

import com.skd.vehiclery.automobile.attachment.FrontAttachmentType;
import com.skd.vehiclery.entity.AutomobileEntity;

public class EmptyFrontAttachment extends FrontAttachment {
    public EmptyFrontAttachment(FrontAttachmentType<?> type, AutomobileEntity automobile) {
        super(type, automobile);
    }
}

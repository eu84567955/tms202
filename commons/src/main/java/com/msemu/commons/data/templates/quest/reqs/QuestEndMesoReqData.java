package com.msemu.commons.data.templates.quest.reqs;

import com.msemu.commons.data.enums.QuestRequirementDataType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Weber on 2018/4/22.
 */
public class QuestEndMesoReqData extends QuestReqData {

    @Getter
    @Setter
    private int meso;

    @Override
    public QuestRequirementDataType getType() {
        return QuestRequirementDataType.endmeso;
    }
}

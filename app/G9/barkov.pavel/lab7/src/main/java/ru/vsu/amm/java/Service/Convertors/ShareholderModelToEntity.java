package ru.vsu.amm.java.Service.Convertors;

import ru.vsu.amm.java.Repository.Entities.Shareholder;
import ru.vsu.amm.java.Service.Entities.ShareholderCreateModel;

public class ShareholderModelToEntity {
    public static Shareholder Convert(ShareholderCreateModel model,String password){
        return new Shareholder(null,password,model.fio(),model.document(),model.email(),model.phone());
    }
}

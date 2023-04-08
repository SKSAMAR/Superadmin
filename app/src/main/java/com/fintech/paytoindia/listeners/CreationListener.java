package com.fintech.paytoindia.listeners;

import com.fintech.paytoindia.data.model.PackageType;
import com.fintech.paytoindia.data.model.PersonUserModel;
import com.fintech.paytoindia.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}

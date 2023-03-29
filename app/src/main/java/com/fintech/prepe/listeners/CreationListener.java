package com.fintech.prepe.listeners;

import com.fintech.prepe.data.model.PackageType;
import com.fintech.prepe.data.model.PersonUserModel;
import com.fintech.prepe.data.model.UserTypeModel;

import java.util.List;

public interface CreationListener {

    void achieveUserType(List<UserTypeModel> userTypeModelList);
    void achievePersonUser(List<PersonUserModel> personUserModelList);
    void achievePackageType(List<PackageType> packageTypeList);

}
